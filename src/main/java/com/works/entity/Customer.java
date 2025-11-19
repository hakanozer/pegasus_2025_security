package com.works.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long cid;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String surname;

    @Column(unique = true, length = 200)
    private String email;

    private String password;

}
