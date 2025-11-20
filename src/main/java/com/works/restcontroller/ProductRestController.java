package com.works.restcontroller;

import com.works.entity.Product;
import com.works.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("save")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("list")
    public Page<Product> list(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        return productService.list(pageNumber);
    }

}
