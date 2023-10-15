package com.guest.note.service;

import com.guest.note.entity.GuestNoteEntity;
import com.guest.note.entity.NoteTypeEntity;
import com.guest.note.entity.UserEntity;
import com.guest.note.exception.ApiException;
import com.guest.note.mapper.GuestNoteMapper;
import com.guest.note.model.GuestNoteDTO;
import com.guest.note.model.ItemCriteria;
import com.guest.note.model.SendGuestNotesDTO;
import com.guest.note.repository.GuestNoteRepository;
import com.guest.note.repository.NoteTypeRepository;
import com.guest.note.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.guest.note.constants.Constant.*;
import static com.guest.note.repository.support.GuestNoteSpecification.*;

@Service
public class GuestNoteService {
    @Autowired
    GuestNoteRepository guestNoteRepository;

    @Autowired
    GuestNoteMapper guestNoteMapper;

    @Autowired
    NoteTypeRepository noteTypeRepository;

    @Autowired
    UserRepository userRepository;
    public List<GuestNoteDTO> getAllGuestNoteDetails(Long userId, ItemCriteria itemCriteria){
        Specification<GuestNoteEntity> filters = Specification.where(guestNoteWithSpecificType(itemCriteria.getNoteTypes()))
                .and(guestNoteWithSpecificUser(userId))
                .and(guestNoteWithinSpecificPeriod());
        Pageable pg = PageRequest.of(itemCriteria.getPage(), itemCriteria.getSize());
        List<GuestNoteEntity> guestNoteEntityList = guestNoteRepository.findAll(filters, pg).getContent();
        List<GuestNoteDTO> guestNoteDTOList = guestNoteEntityList.stream().map(guestNote -> guestNoteMapper.guestNoteEntityToGuestNoteDTO(guestNote)).collect(Collectors.toList());
        return guestNoteDTOList;
    }

    public List<GuestNoteDTO> sendGuestNote(SendGuestNotesDTO sendGuestNotesDTO){
        List<Long> userIds = sendGuestNotesDTO.getUsers();
        GuestNoteDTO guestNoteDTO = sendGuestNotesDTO.getGuestNote();
        Optional<NoteTypeEntity> noteType = noteTypeRepository.findById(guestNoteDTO.getTypeId());
        if(!noteType.isPresent()){
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format(NOTE_TYPE_NOT_FOUND,guestNoteDTO.getTypeId()));
        }
        if(!noteType.get().getEnabled()){
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format(NOTE_TYPE_NOT_ENABLED,guestNoteDTO.getTypeId()));
        }
        GuestNoteEntity guestNoteEntity = guestNoteMapper.guestNoteDTOtoGuestNoteEntity(guestNoteDTO);
        List<GuestNoteEntity> guestNoteEntityList = new ArrayList<>();
        for(Long id:userIds){
            Optional<UserEntity> user = userRepository.findById(id);
            GuestNoteEntity guestNote = (GuestNoteEntity) guestNoteEntity.clone();
            if(!user.isPresent()){
                throw new ApiException(HttpStatus.BAD_REQUEST, String.format(USER_NOT_FOUND,id));
            }
            guestNote.setUser(user.get());
            guestNote.setType(noteType.get());
            guestNote.setCreationDate(OffsetDateTime.now());
            guestNoteEntityList.add(guestNote);
        }
        guestNoteRepository.saveAll(guestNoteEntityList);
        return guestNoteEntityList.stream().map(entity -> guestNoteMapper.guestNoteEntityToGuestNoteDTO(entity)).collect(Collectors.toList());
    }

    public List<Long> deleteGuestNotes(List<Long> guestNoteIds){
        List<Long> notDeletedIds = new ArrayList<>();
        for(Long id:guestNoteIds){
            try {
                guestNoteRepository.deleteById(id);
            }catch(Exception e) {
                notDeletedIds.add(id);
            }
        }
        return notDeletedIds;
    }

    public List<GuestNoteEntity> getLatestGuestNotes(){
        Specification<GuestNoteEntity> filters = Specification.where(latestGuestNotes())
                .and(guestNoteWithNotificationEnabled());
        List<GuestNoteEntity> guestNoteEntityList = guestNoteRepository.findAll(filters);
        return guestNoteEntityList;
    }

}
