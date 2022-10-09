package com.example.neoflex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CalendarHolidayDTO", description = "Сущность хранилища списка выходных и праздников")
public class CalendarHolidayDTO {

    private ArrayList<String> holidays;
    private ArrayList<String> preholidays;
    private ArrayList<String> nowork;

}
