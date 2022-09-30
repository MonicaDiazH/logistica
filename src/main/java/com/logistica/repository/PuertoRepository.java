package com.logistica.repository;

import com.logistica.domain.Puerto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PuertoRepository extends PagingAndSortingRepository<Puerto, Integer>, JpaSpecificationExecutor<Puerto> {

}