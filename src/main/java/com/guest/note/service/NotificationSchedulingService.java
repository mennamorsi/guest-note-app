package com.guest.note.service;


import com.guest.note.entity.GuestNoteEntity;
import com.guest.note.entity.NoteTypeEntity;
import com.guest.note.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class NotificationSchedulingService {

    @Autowired
    GuestNoteService guestNoteService;

    @Scheduled(cron = "0 0 15 * * ?")
//    @Scheduled(cron = "0 * * * * ?")
    public void scanForGuestNotes() {
       List<GuestNoteEntity> guestNoteEntityList = guestNoteService.getLatestGuestNotes();
        Map<UserEntity, List<NoteTypeEntity>> result = guestNoteEntityList.stream()
                .collect(Collectors.groupingBy(p->p.getUser(), Collectors.mapping(p->p.getType(), Collectors.toList())));
        for (Map.Entry<UserEntity, List<NoteTypeEntity>> entry : result.entrySet()) {
            Map<NoteTypeEntity,Integer> typeCounts = entry.getValue().stream()
                    .collect( Collectors.groupingBy( Function.identity(), Collectors.summingInt(e -> 1) ));
            System.out.println(String.format("Push notification for user id: %s",entry.getKey().getId()));
            System.out.print(String.format("User id: %s got ",entry.getKey().getId()));
            for (Map.Entry<NoteTypeEntity, Integer> en : typeCounts.entrySet()) {
                System.out.println(String.format("%s new %s notes",en.getValue(),en.getKey().getName()));
            }
        }
    }


}
