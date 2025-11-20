package com.works.restcontroller;

import com.works.entity.Note;
import com.works.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/note")
@RequiredArgsConstructor
public class NoteRestController {

    private final NoteService noteService;

    @PostMapping("save")
    public Note save(@RequestBody Note note) {
        return noteService.save(note);
    }

    @GetMapping("list")
    public Page<Note> list(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber){
        return noteService.list(pageNumber);
    }

}
