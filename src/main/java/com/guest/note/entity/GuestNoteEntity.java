package com.guest.note.entity;

import com.guest.note.utils.ListStringConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "notes")
@SQLDelete(sql = "UPDATE notes SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class GuestNoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    @NotBlank(message = "Invalid title: Empty title")
    @NotNull(message = "Invalid title: title is NULL")
    private String title;
    @Column(name = "message_body")
    @NotBlank(message = "Invalid message body: Empty message body")
    @NotNull(message = "Invalid message body: message body is NULL")
    private String msgBody;

    @NotNull(message = "Invalid type: type is NULL")
    @OneToOne()
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private NoteTypeEntity type;

    @NotNull(message = "Invalid user: user is NULL")
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "attached_files")
    @Convert(converter = ListStringConverter.class)
    private List<String> attachedFiles;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    public GuestNoteEntity(Long id, String title, String msgBody, NoteTypeEntity type, UserEntity user, Boolean deleted, List<String> attachedFiles) {
        this.id = id;
        this.title = title;
        this.msgBody = msgBody;
        this.type = type;
        this.user = user;
        this.deleted = deleted;
        this.attachedFiles = attachedFiles;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new GuestNoteEntity(this.id,this.title,this.msgBody,this.type,this.user,this.deleted,this.attachedFiles);
        }
    }
}
