package com.guest.note.repository.support;


import com.guest.note.entity.GuestNoteEntity;
import com.guest.note.entity.NoteTypeEntity;
import com.guest.note.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

public class GuestNoteSpecification {

    private GuestNoteSpecification() {}

    public static Specification<GuestNoteEntity> guestNoteWithSpecificType(List<Long> types) {
        return (root, query, builder) -> root.get("type").get("id").in(types);
    }

    public static Specification<GuestNoteEntity> guestNoteWithSpecificUser(Long user) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), user);
    }

    public static Specification<GuestNoteEntity> guestNoteWithinSpecificPeriod() {
        return (root, query, builder) -> {
            OffsetDateTime today = OffsetDateTime.now();
            OffsetDateTime date = today.minusDays(30);
            return builder.greaterThanOrEqualTo(root.get("creationDate"), date);
        };
    }

    public static Specification<GuestNoteEntity> guestNoteWithNotificationEnabled() {
        return (root, query, builder) -> {
            return builder.equal(root.get("user").get("notificationEnabled"), Boolean.TRUE);
        };
    }

    public static Specification<GuestNoteEntity> latestGuestNotes() {
        return (root, query, builder) -> {
            OffsetDateTime today = OffsetDateTime.now();
            today = today.with(ChronoField.HOUR_OF_DAY, 0);
            return builder.greaterThanOrEqualTo(root.get("creationDate"), today);
        };
    }

//    public static Specification<GuestNoteEntity> groupByUserId() {
//        return (root, query, builder) -> {
//            query.groupBy(root.get("user").get("id"));
//            return builder.and();
//        };
//    }

}
