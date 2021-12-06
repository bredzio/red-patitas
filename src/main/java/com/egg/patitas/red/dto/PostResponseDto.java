package com.egg.patitas.red.dto;

import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.Zone;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
        private Pet pet;
        private Zone zone;
        private byte[] photo;
}

