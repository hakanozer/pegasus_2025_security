package com.works.repository;

import com.works.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}