package com.guest.note.mapper;

import com.guest.note.entity.GuestNoteEntity;
import com.guest.note.model.GuestNoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestNoteMapper {
    @Mapping(source ="user.id", target = "userId")
    @Mapping(source ="type.id", target = "typeId")
    public abstract GuestNoteDTO guestNoteEntityToGuestNoteDTO(GuestNoteEntity guestNote);

    @Mapping(target = "deleted" , constant = "false")
    public abstract GuestNoteEntity guestNoteDTOtoGuestNoteEntity(GuestNoteDTO guestNote);
}
