package com.logistica.repository;

import com.logistica.domain.TipoProducto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TipoProductoRepository extends PagingAndSortingRepository<TipoProducto, Integer>, JpaSpecificationExecutor<TipoProducto> {

}