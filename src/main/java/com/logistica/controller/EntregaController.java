package com.logistica.controller;

import com.logistica.domain.Entrega;
import com.logistica.domain.utils.EntregaPagingResponse;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.service.EntregaService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/entrega")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Transactional
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Entrega> create(@RequestBody Entrega item) {
        return new ResponseEntity<>(entregaService.create(item), HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Entrega update(@PathVariable(name = "id") Integer id, @RequestBody Entrega item) {
        return entregaService.update(id, item);
    }

    @Transactional
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id) {
        entregaService.delete(id);
    }

    @Transactional
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Entrega> get(@PathVariable(name = "id") Integer id) {
        return entregaService.get(id)
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Entrega>> get(
            @And({
                    @Spec(path = "fechaRegistro", params = "fechaRegistro", spec = Equal.class),
                    @Spec(path = "fechaEntrega", params = "fechaEntrega", spec = Equal.class),
                    @Spec(path = "precioNormal", params = "precioNormal", spec = Equal.class),
                    @Spec(path = "descuento", params = "descuento", spec = Equal.class),
                    @Spec(path = "precioFinal", params = "precioFinal", spec = Equal.class),
                    @Spec(path = "numeroGuia", params = "numeroGuia", spec = Equal.class),
                    @Spec(path = "tipoEntrega", params = "tipoEntrega", spec = Equal.class)
            }) Specification<Entrega> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final EntregaPagingResponse response = entregaService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(EntregaPagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}