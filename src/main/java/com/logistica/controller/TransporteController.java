package com.logistica.controller;

import com.logistica.domain.Transporte;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.domain.utils.TransportePagingResponse;
import com.logistica.service.TransporteService;
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
@RequestMapping("/transporte")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;

    @Transactional
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Transporte> create(@RequestBody Transporte item) {
        return new ResponseEntity<>(transporteService.create(item), HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Transporte update(@PathVariable(name = "id") Integer id, @RequestBody Transporte item) {
        return transporteService.update(id, item);
    }

    @Transactional
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id) {
        transporteService.delete(id);
    }

    @Transactional
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Transporte> get(@PathVariable(name = "id") Integer id) {
        return transporteService.get(id)
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Transporte>> get(
            @And({
                    @Spec(path = "descripcion", params = "descripcion", spec = Like.class),
                    @Spec(path = "placa", params = "placa", spec = Equal.class),
                    @Spec(path = "flota", params = "flota", spec = Equal.class),
                    @Spec(path = "tipoTransporte", params = "tipoTransporte", spec = Equal.class)
            }) Specification<Transporte> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final TransportePagingResponse response = transporteService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(TransportePagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}