package com.guest.note.repository;

import com.guest.note.entity.GuestNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestNoteRepository extends JpaRepository<GuestNoteEntity,Long>, JpaSpecificationExecutor<GuestNoteEntity> {
}
