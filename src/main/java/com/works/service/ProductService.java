package com.works.service;

import com.works.entity.Product;
import com.works.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @CacheEvict(value = "products", allEntries = true)
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Cacheable("products")
    public Page<Product> list(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 3);
        return productRepository.findAll(pageable);
    }

}
