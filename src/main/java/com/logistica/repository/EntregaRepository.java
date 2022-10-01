package com.logistica.repository;

import com.logistica.domain.Entrega;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EntregaRepository extends PagingAndSortingRepository<Entrega, Integer>, JpaSpecificationExecutor<Entrega> {

    @Query("select b from Cliente a join a.entregaes b where a.id = ?1")
    List<Entrega> findAllByClienteId(Integer clienteId);
}