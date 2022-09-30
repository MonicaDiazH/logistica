package com.logistica.repository;

import com.logistica.domain.Entrega;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EntregaRepository extends PagingAndSortingRepository<Entrega, Integer>, JpaSpecificationExecutor<Entrega> {

}