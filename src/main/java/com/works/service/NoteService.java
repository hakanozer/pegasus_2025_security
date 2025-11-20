package com.works.service;

import com.works.entity.Note;
import com.works.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public Page<Note> list(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 3);
        return noteRepository.findAll(pageable);
    }


}
