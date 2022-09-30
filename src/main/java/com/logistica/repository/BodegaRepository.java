package com.logistica.repository;

import com.logistica.domain.Bodega;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BodegaRepository extends PagingAndSortingRepository<Bodega, Integer>, JpaSpecificationExecutor<Bodega> {

}