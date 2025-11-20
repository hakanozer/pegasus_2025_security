package com.works.restcontroller;

import com.works.entity.Note;
import com.works.entity.Product;
import com.works.service.NoteService;
import com.works.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final ProductService productService;
    private final NoteService noteService;

    @GetMapping("product/list")
    public List<Product> productList(){
        return productService.list();
    }

    @GetMapping("note/list")
    public List<Note> noteList(){
        return noteService.list();
    }


}
