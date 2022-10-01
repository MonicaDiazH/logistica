package com.logistica.controller;

import com.logistica.domain.TipoProducto;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.domain.utils.TipoProductoPagingResponse;
import com.logistica.service.TipoProductoService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
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
@RequestMapping("/tipoProducto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    @Transactional
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoProducto> create(@RequestBody TipoProducto item) {
        return new ResponseEntity<>(tipoProductoService.create(item), HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TipoProducto update(@PathVariable(name = "id") Integer id, @RequestBody TipoProducto item) {
        return tipoProductoService.update(id, item);
    }

    @Transactional
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id) {
        tipoProductoService.delete(id);
    }

    @Transactional
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TipoProducto> get(@PathVariable(name = "id") Integer id) {
        return tipoProductoService.get(id)
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TipoProducto>> get(
            @And({
                    @Spec(path = "nombre", params = "nombre", spec = Like.class),
                    @Spec(path = "descripcion", params = "descripcion", spec = Like.class),
                    @Spec(path = "precio", params = "precio", spec = Equal.class)
            }) Specification<TipoProducto> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final TipoProductoPagingResponse response = tipoProductoService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(TipoProductoPagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}