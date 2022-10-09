package com.example.neoflex.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "VacationDataDTO", description = "Сущность вводимых пользователем данных")
public class VacationDataDTO {

    private Long yearSalary;
    private Integer numberOfVacationDays;
    private LocalDate startVacation;
    private LocalDate endVacation;

}
