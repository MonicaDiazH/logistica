package com.logistica.repository;

import com.logistica.domain.Cliente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {

}