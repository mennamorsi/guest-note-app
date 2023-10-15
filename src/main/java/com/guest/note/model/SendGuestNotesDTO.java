package com.guest.note.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendGuestNotesDTO {
    @Valid
    private GuestNoteDTO guestNote;

    @NotNull(message = "Invalid users: users is NULL")
    private List<Long> users;

}
