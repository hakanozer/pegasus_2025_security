package com.works.service;

import com.works.dto.CustomerRegisterDto;
import com.works.entity.Customer;
import com.works.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public Customer register(CustomerRegisterDto customerRegisterDto) {
        Customer customer = modelMapper.map(customerRegisterDto, Customer.class);
        return customerRepository.save(customer);
    }

}
