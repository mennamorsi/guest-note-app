package com.guest.note.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GuestNoteDTO {
    private Long id;
    @NotBlank(message = "Invalid title: Empty title")
    @NotNull(message = "Invalid title: title is NULL")
    private String title;
    @NotBlank(message = "Invalid message body: Empty message body")
    @NotNull(message = "Invalid message body: message body is NULL")
    private String msgBody;
    @NotNull(message = "Invalid type: type is NULL")
    private Long typeId;

    private Long userId;
    private List<String> attachedFiles;
    private OffsetDateTime creationDate;

}
