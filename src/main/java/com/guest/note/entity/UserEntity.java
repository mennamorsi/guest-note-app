package com.guest.note.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "pp_link")
    private String ppLink;
    @Column(name = "notification_enabled")
    private Boolean notificationEnabled = true;

    public UserEntity(Long id) {
        this.id = id;
    }
}
