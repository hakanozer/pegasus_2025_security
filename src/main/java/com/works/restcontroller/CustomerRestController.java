package com.works.restcontroller;

import com.works.dto.CustomerRegisterDto;
import com.works.entity.Customer;
import com.works.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;

    @PostMapping("register")
    public Customer register(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
        return customerService.register(customerRegisterDto);
    }

    @GetMapping("control/{name}/{surname}")
    public boolean control(@PathVariable String name, @PathVariable String surname) {
        return customerService.control(name, surname);
    }

}
