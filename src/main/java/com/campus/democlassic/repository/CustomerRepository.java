package com.campus.democlassic.repository;

import com.campus.democlassic.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {

        List<Customer> findAll();

        Customer findCustomerById(Long id);

}
