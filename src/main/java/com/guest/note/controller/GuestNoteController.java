package com.guest.note.controller;


import com.guest.note.entity.GuestNoteEntity;
import com.guest.note.entity.UserEntity;
import com.guest.note.model.GuestNoteDTO;
import com.guest.note.model.ItemCriteria;
import com.guest.note.model.SendGuestNotesDTO;
import com.guest.note.service.GuestNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestNoteController {
    @Autowired
    GuestNoteService guestNoteService;

    @PostMapping("/guestNote")
    public ResponseEntity<List<GuestNoteDTO>> sendGuestNote(@Validated @RequestBody SendGuestNotesDTO sendGuestNotesDTO){
        List<GuestNoteDTO> guestNoteDTOList = guestNoteService.sendGuestNote(sendGuestNotesDTO);
        return new ResponseEntity<>(guestNoteDTOList, HttpStatus.OK);
    }

    @GetMapping("/guestNotes/{userId}")
    public ResponseEntity<List<GuestNoteDTO>> getAllGuestNoteDetails(@PathVariable @Valid Long userId, ItemCriteria itemCriteria){
        List<GuestNoteDTO> guestNoteDTOList = guestNoteService.getAllGuestNoteDetails(userId,itemCriteria);
        return new ResponseEntity<>(guestNoteDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/guestNotes/{guestNoteIds}")
    public ResponseEntity<List<Long>> deleteGuestNotes(@PathVariable @Valid List<Long> guestNoteIds){
        List<Long> notDeletedIds = guestNoteService.deleteGuestNotes(guestNoteIds);
        return new ResponseEntity<>(notDeletedIds, HttpStatus.OK);
    }

  }
