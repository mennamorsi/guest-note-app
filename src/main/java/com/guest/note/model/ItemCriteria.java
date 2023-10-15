package com.guest.note.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemCriteria {
    private int page = 0;
    private int size = 25;
    private List<Long> noteTypes;
}
