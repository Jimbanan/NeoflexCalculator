package com.example.neoflex.controller;

import com.example.neoflex.dto.VacationDataDTO;
import com.example.neoflex.service.CalculatorSalaryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@RestController
@Api
@Slf4j
public class CalculateController {
    @Autowired
    CalculatorSalaryServiceImpl calculatorSalaryService;

    @ApiOperation(value = "Api для расчета суммы отпускных на основе годовой зарплаты, даты начала отпуска, даты окончания отпуска и количества дней в отпуске")
    @GetMapping("/calculate")
    public ResponseEntity<String> calculate(
            @ApiParam(required = true, value = "Сумма годовой зарплаты") @RequestParam Long yearSalary,
            @ApiParam(required = true, value = "Количество дней в отпуске") @RequestParam Integer numberOfVacationDays,
            @ApiParam(required = true, value = "Дата начала отпуска (YYYY-MMM-DD)") @RequestParam String startVacation,
            @ApiParam(required = true, value = "Дата окончания отпуска (YYYY-MMM-DD)") @RequestParam String endVacation) {

        log.trace("CalculateController.calculate: получена сумма полученная сотрудником за год: " + yearSalary);
        log.trace("CalculateController.calculate: получено количество дней в отпуске: " + numberOfVacationDays);

        if (!startVacation.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new ResponseEntity<>("Дата начала отпуска имеет некорректный вид", HttpStatus.BAD_REQUEST);
        }
        if (!endVacation.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new ResponseEntity<>("Дата окончания отпуска имеет некорректный вид", HttpStatus.BAD_REQUEST);
        }

        if (Period.between(LocalDate.parse(startVacation), LocalDate.parse(endVacation)).getDays() < 0) {
            return new ResponseEntity<>("Дата начала отпуска, меньше даты окончания", HttpStatus.BAD_REQUEST);
        }

        log.trace("CalculateController.calculate: получена дата начала отпуска: " + startVacation);
        log.trace("CalculateController.calculate: получена дата окончания отпуска: " + endVacation);

        try {
            BigDecimal vacationPayCalculator = calculatorSalaryService.vacationPayCalculator(VacationDataDTO.builder()
                    .yearSalary(yearSalary)
                    .numberOfVacationDays(numberOfVacationDays)
                    .startVacation(LocalDate.parse(startVacation))
                    .endVacation(LocalDate.parse(endVacation))
                    .build());

            log.trace("CalculateController.calculate: расчитана сумма отпускных пользователя: " + endVacation);

            return new ResponseEntity<>(
                    "Сумма отпускных равна: " + vacationPayCalculator, HttpStatus.OK);
        } catch (ArithmeticException | NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
