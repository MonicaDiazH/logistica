package com.logistica.repository;

import com.logistica.domain.EntregaTipoProducto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EntregaTipoProductoRepository extends PagingAndSortingRepository<EntregaTipoProducto, Integer>, JpaSpecificationExecutor<EntregaTipoProducto> {

    @Query("select b from Entrega a join a.entregaTipoProductoes b where a.id = ?1")
    List<EntregaTipoProducto> findAllByIdEntrega(Integer idEntrega);
}