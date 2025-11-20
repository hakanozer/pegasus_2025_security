package com.works.restcontroller;

import com.works.entity.Note;
import com.works.entity.Product;
import com.works.service.NoteService;
import com.works.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final ProductService productService;
    private final NoteService noteService;

    @GetMapping("product/list")
    public Page<Product> productList(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber){
        return productService.list(pageNumber);
    }

    @GetMapping("note/list")
    public Page<Note> noteList(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber){
        return noteService.list(pageNumber);
    }


}
