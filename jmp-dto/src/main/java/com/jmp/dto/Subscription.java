package com.jmp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    String bankcard;
    LocalDate startDate;

}
