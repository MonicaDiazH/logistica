package com.logistica.controller;

import com.logistica.domain.Cliente;
import com.logistica.domain.utils.ClientePagingResponse;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Transactional
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> create(@RequestBody Cliente item) {
        return new ResponseEntity<>(clienteService.create(item), HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Cliente update(@PathVariable(name = "id") Integer id, @RequestBody Cliente item) {
        return clienteService.update(id, item);
    }

    @Transactional
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id) {
        clienteService.delete(id);
    }

    @Transactional
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> get(@PathVariable(name = "id") Integer id) {
        return clienteService.get(id)
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Cliente>> get(
            @And({
                    @Spec(path = "nombre", params = "nombre", spec = Like.class),
                    @Spec(path = "telefono", params = "telefono", spec = Like.class),
                    @Spec(path = "direccion", params = "direccion", spec = Like.class)
            }) Specification<Cliente> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final ClientePagingResponse response = clienteService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(ClientePagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}