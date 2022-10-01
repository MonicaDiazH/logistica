package com.logistica.repository;

import com.logistica.domain.Transporte;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransporteRepository extends PagingAndSortingRepository<Transporte, Integer>, JpaSpecificationExecutor<Transporte> {

}