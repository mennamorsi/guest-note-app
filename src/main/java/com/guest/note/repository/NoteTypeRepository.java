package com.guest.note.repository;

import com.guest.note.entity.NoteTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteTypeRepository extends JpaRepository<NoteTypeEntity,Long> {
}
