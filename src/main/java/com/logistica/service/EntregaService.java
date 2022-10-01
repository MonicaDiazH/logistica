package com.logistica.service;

import com.logistica.domain.*;
import com.logistica.domain.dto.EntregaDTO;
import com.logistica.domain.utils.EntregaPagingResponse;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregaTipoProductoRepository entregaTipoProductoRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private PuertoRepository puertoRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    /**
     * delete element
     *
     * @param id element ID
     * @throws EntityNotFoundException Exception when retrieve entity
     */
    public void delete(Integer id) {
        Entrega entity = entregaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can not find the entity bodega (%s = %s).", "id", id)));
        entregaRepository.delete(entity);
    }

    /**
     * @param id element ID
     * @return element
     */
    public Optional<Entrega> get(Integer id) {
        return entregaRepository.findById(id);
    }

    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public EntregaPagingResponse get(Specification<Entrega> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Entrega> entities = get(spec, sort);
            return new EntregaPagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public EntregaPagingResponse get(Specification<Entrega> spec, Pageable pageable) {
        Page<Entrega> page = entregaRepository.findAll(spec, pageable);
        List<Entrega> content = page.getContent();
        return new EntregaPagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<Entrega> get(Specification<Entrega> spec, Sort sort) {
        return entregaRepository.findAll(spec, sort);
    }

    /**
     * create element.
     *
     * @param item element to create
     * @return element after creation
     */
    public Entrega create(EntregaDTO item) {
        Entrega entrega = validateCreateEntrega(item);
        if (entrega != null) {
            Entrega entregaNew = save(entrega);
            if (validateDetailEntrega(item.getEntregaTipoProductos())) {
                return saveDetailEntrega(item.getEntregaTipoProductos(), entregaNew);
            } else return new Entrega();
        } else return new Entrega();
    }

    public Entrega validateCreateEntrega(EntregaDTO item) {
        Entrega entregaComplete = item.getEntrega();

        //Validando si tipo de entrega es terrestre y verificando que los valores existan en la bdd
        if (item.getEntrega().getTipoEntrega().equals("TERRESTRE")) {
            if (item.getBodegaId() != null) {
                Bodega bodega = bodegaRepository.findById(item.getBodegaId()).orElse(null);

                if (bodega == null) {
                    return null;
                } else {
                    entregaComplete.setBodega(bodega);
                }
            } else return null;
        }

        //Validando si tipo de entrega es maritima y verificando que los valores existan en la bdd
        if (item.getEntrega().getTipoEntrega().equals("MARITIMA")) {
            if (item.getPuertoId() != null) {
                Puerto puerto = puertoRepository.findById(item.getPuertoId()).orElse(null);
                if (puerto == null) {
                    return null;
                } else {
                    entregaComplete.setPuerto(puerto);
                }
            } else return null;
        }

        //Validando que exista el cliente seleccionado
        Cliente cliente = clienteRepository.findById(item.getClienteId()).orElse(null);
        if (cliente == null) {
            return null;
        } else {
            entregaComplete.setCliente(cliente);
        }

        //Validando que exista el transporte seleccionado
        Transporte transporte = transporteRepository.findById(item.getTransporteId()).orElse(null);
        if (transporte == null) {
            return null;
        } else {
            entregaComplete.setTransporte(transporte);
        }

        return entregaComplete;
    }

    public boolean validateDetailEntrega(List<EntregaTipoProducto> entregaTipoProductos) {
        for (EntregaTipoProducto entregaTipoProducto : entregaTipoProductos) {
            TipoProducto tipoProducto = tipoProductoRepository.findById(entregaTipoProducto.getTipoProducto().getId()).orElse(null);
            if (tipoProducto == null) {
                return false;
            }
        }
        return true;
    }

    public Entrega saveDetailEntrega(List<EntregaTipoProducto> entregaTipoProductos, Entrega entrega) {
        for (EntregaTipoProducto entregaTipoProducto : entregaTipoProductos) {
            TipoProducto tipoProducto = tipoProductoRepository.findById(entregaTipoProducto.getTipoProducto().getId()).orElse(null);
            if (tipoProducto != null) {
                entregaTipoProducto.setPrecio(tipoProducto.getPrecio());
                entregaTipoProducto.setTotal(tipoProducto.getPrecio().multiply(BigDecimal.valueOf(entregaTipoProducto.getCantidad())));
                entregaTipoProducto.setEntrega(entrega);
                entregaTipoProducto.setTipoProducto(tipoProducto);
                entregaTipoProductoRepository.save(entregaTipoProducto);
            }
        }
        Integer cantProductos = entregaTipoProductoRepository.getCantidadProductos(entrega.getId());
        BigDecimal descuento = BigDecimal.ZERO;
        BigDecimal precioNormal = entregaTipoProductoRepository.getTotalMontoProductos(entrega.getId());
        entrega.setPrecioNormal(precioNormal);

        if (cantProductos > 10) {//Aplica descuento si la cantidad de productos es superior a 10
            if (entrega.getTipoEntrega().equals("TERRESTRE")) {//Aplica 5%
                descuento = precioNormal.multiply(BigDecimal.valueOf(0.05));
            } else if (entrega.getTipoEntrega().equals("MARITIMA")) {//Aplica 3%
                descuento = precioNormal.multiply(BigDecimal.valueOf(0.03));
            }
        }
        entrega.setDescuento(descuento);
        entrega.setPrecioFinal(precioNormal.subtract(descuento));
        save(entrega);
        return entrega;
    }

    /**
     * update element
     *
     * @param id   element identifier
     * @param item element to update
     * @return element after update
     * @throws EntityNotFoundException Exception when retrieve entity
     */
    public Entrega update(Integer id, Entrega item) {
        if (item.getId() == null) {
            throw new RuntimeException("Can not update entity, entity without ID.");
        } else if (!id.equals(item.getId())) {
            throw new RuntimeException(String.format("Can not update entity, the resource ID (%d) not match the objet ID (%d).", id, item.getId()));
        }
        return save(item);
    }

    /**
     * create \ update elements
     *
     * @param item element to save
     * @return element after save
     */
    protected Entrega save(Entrega item) {
        return entregaRepository.save(item);
    }

    /**
     * get detail entrega
     *
     * @return List EntregaTipoProducto
     */
    public List<EntregaTipoProducto> getEntregaDetail(Integer idEntrega) {
        return entregaTipoProductoRepository.findAllByIdEntrega(idEntrega);
    }
}