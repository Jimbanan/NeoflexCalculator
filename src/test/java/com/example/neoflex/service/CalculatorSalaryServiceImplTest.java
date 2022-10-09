package com.example.neoflex.service;

import com.example.neoflex.dto.VacationDataDTO;
import com.example.neoflex.test_data.CalculationTestDataInCalculatorSalaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
class CalculatorSalaryServiceImplTest {

    @Spy
    CalculatorSalaryServiceImpl calculatorSalaryService = new CalculatorSalaryServiceImpl();

    @BeforeEach
    void createReflection() {
        ReflectionTestUtils.setField(calculatorSalaryService, "FILE_URL", "https://raw.githubusercontent.com/d10xa/holidays-calendar/master/json/calendar.json");
        ReflectionTestUtils.setField(calculatorSalaryService, "FILE_LOCATION", "src/main/resources/calendar.json");
    }

    @Test
    void getCorrectVacationPayCalculator() throws Exception {
        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getCorrectTest();
        Assertions.assertEquals(BigDecimal.valueOf(71652), calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));
    }

    @Test
    void getCorrectZeroYearSalary() throws Exception {
        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getCorrectZeroYearSalary();
        Assertions.assertEquals(BigDecimal.valueOf(0), calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));
    }

    @Test
    void getCorrectZeroNumberOfVacationDays() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getCorrectZeroNumberOfVacationDays();
        Assertions.assertEquals(BigDecimal.valueOf(21), calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));
    }

    @Test
    void getWrongStartVacationMoreEndVacation() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getWrongStartVacationMoreEndVacation();
        Assertions.assertThrows(Exception.class, () -> calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));
    }

    @Test
    void getCorrectNullStartVacationAndEndVacation() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getCorrectNullStartVacationAndEndVacation();
        Assertions.assertEquals(BigDecimal.valueOf(510), calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));

    }

    @Test
    void getWrongNullYearSalary() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getWrongNullYearSalary();
        Assertions.assertThrows(NullPointerException.class, () -> calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));

    }

    @Test
    void getWrongNullNumberOfVacationDays() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getWrongNullNumberOfVacationDays();
        Assertions.assertThrows(NullPointerException.class, () -> calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));

    }

    @Test
    void getWrongNullYearSalaryAndNumberOfVacationDays() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getWrongNullYearSalaryAndNumberOfVacationDays();
        Assertions.assertThrows(NullPointerException.class, () -> calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));

    }

    @Test
    void getCorrectHolidays() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInCalculatorSalaryService.getCorrectHolidays();
        Assertions.assertEquals(BigDecimal.valueOf(374), calculatorSalaryService.vacationPayCalculator(vacationDataDTOTest));

    }
}