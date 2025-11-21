package com.works.service;

import com.works.dto.CustomerRegisterDto;
import com.works.entity.Customer;
import com.works.entity.Role;
import com.works.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final DB db;
    private final TinkEncDec tinkEncDec;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(username);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            String plainTc = tinkEncDec.decrypt(customer.getTc());
            System.out.println(plainTc);
            return new User(
                    customer.getEmail(),
                    customer.getPassword(),
                    parseRole(customer.getRoles())
            );
        }
        throw new UsernameNotFoundException("User not found with username:" + username);
    }

    private Collection<? extends GrantedAuthority> parseRole(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    public Customer register(CustomerRegisterDto customerRegisterDto) {
        Customer customer = modelMapper.map(customerRegisterDto, Customer.class);
        String newPass = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(newPass);
        Role role = new Role();
        role.setRid(3l);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        customer.setRoles(roles);
        String encryptTc = tinkEncDec.encrypt(customer.getTc());
        customer.setTc(encryptTc);
        return customerRepository.save(customer);
    }


    public boolean control(String name, String surname) {
        try {
            String sql = "SELECT * FROM customer WHERE name = ? AND surname = '"+surname+"'";
            PreparedStatement st = db.dataSource().getConnection().prepareStatement(sql);
            st.setString(1, name);
            //st.setString(2, surname);
            ResultSet rs = st.executeQuery();
            return rs.next();
        }catch (Exception e){
            return false;
        }
    }


}
