package com.works.service;

import com.works.dto.CustomerRegisterDto;
import com.works.entity.Customer;
import com.works.entity.Role;
import com.works.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public Customer register(CustomerRegisterDto customerRegisterDto) {
        Customer customer = modelMapper.map(customerRegisterDto, Customer.class);
        String newPass = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(newPass);
        Role role = new Role();
        role.setRid(3l);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        customer.setRoles(roles);
        return customerRepository.save(customer);
    }

}
