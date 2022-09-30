package com.logistica.service;

import com.logistica.domain.Entrega;
import com.logistica.domain.utils.EntregaPagingResponse;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.repository.EntregaRepository;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

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
     * //     * @throws CreateWithIdException   Exception lancée lors de la création d'un objet existant
     * @throws EntityNotFoundException Exception lors de récupération de l'entité en base
     */
    public Entrega create(Entrega item) {
        return save(item);
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
}