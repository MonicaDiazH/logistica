package com.logistica.service;

import com.logistica.domain.Bodega;
import com.logistica.domain.utils.PagingHeaders;
import com.logistica.domain.utils.BodegaPagingResponse;
import com.logistica.repository.BodegaRepository;;
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
public class BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    /**
     * delete element
     *
     * @param id element ID
     * @throws EntityNotFoundException Exception when retrieve entity
     */
    public void delete(Integer id) {
        Bodega entity = bodegaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can not find the entity bodega (%s = %s).", "id", id)));
        bodegaRepository.delete(entity);
    }

    /**
     * @param id element ID
     * @return element
     */
    public Optional<Bodega> get(Integer id) {
        return bodegaRepository.findById(id);
    }

    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public BodegaPagingResponse get(Specification<Bodega> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Bodega> entities = get(spec, sort);
            return new BodegaPagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
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
    public BodegaPagingResponse get(Specification<Bodega> spec, Pageable pageable) {
        Page<Bodega> page = bodegaRepository.findAll(spec, pageable);
        List<Bodega> content = page.getContent();
        return new BodegaPagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<Bodega> get(Specification<Bodega> spec, Sort sort) {
        return bodegaRepository.findAll(spec, sort);
    }

    /**
     * create element.
     *
     * @param item element to create
     * @return element after creation
     */
    public Bodega create(Bodega item) {
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
    public Bodega update(Integer id, Bodega item) {
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
    protected Bodega save(Bodega item) {
        return bodegaRepository.save(item);
    }
}