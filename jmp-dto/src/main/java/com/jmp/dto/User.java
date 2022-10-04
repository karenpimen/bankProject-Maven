package com.jmp.dto;
import lombok.*;

import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    int id;
    String name;
    String surname;
    LocalDate birthday;

}
