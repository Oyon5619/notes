package com.notes.service;

import com.notes.mapper.NotesMapper;
import com.notes.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    @Autowired
    PhotoMapper photoMapper;

    @Autowired
    NotesMapper notesMapper;
}
