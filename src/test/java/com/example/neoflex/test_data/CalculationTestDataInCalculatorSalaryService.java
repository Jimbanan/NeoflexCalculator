package com.example.neoflex.test_data;

import com.example.neoflex.dto.VacationDataDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CalculationTestDataInCalculatorSalaryService {

    static String jsonPath = "src/main/resources/testParamsInCalculatorSalaryService.json";

    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private static List<VacationDataDTO> getVacationData() throws IOException {
        return objectMapper.readValue(new File(jsonPath), new TypeReference<>() {
        });
    }

    public static VacationDataDTO getCorrectTest() throws IOException {
        return getVacationData().get(0);
    }

    public static VacationDataDTO getCorrectZeroYearSalary() throws IOException {
        return getVacationData().get(1);
    }

    public static VacationDataDTO getCorrectZeroNumberOfVacationDays() throws IOException {
        return getVacationData().get(2);
    }

    public static VacationDataDTO getWrongStartVacationMoreEndVacation() throws IOException {
        return getVacationData().get(3);
    }

    public static VacationDataDTO getCorrectNullStartVacationAndEndVacation() throws IOException {
        return getVacationData().get(4);
    }

    public static VacationDataDTO getWrongNullYearSalary() throws IOException {
        return getVacationData().get(5);
    }

    public static VacationDataDTO getWrongNullNumberOfVacationDays() throws IOException {
        return getVacationData().get(6);
    }

    public static VacationDataDTO getWrongNullYearSalaryAndNumberOfVacationDays() throws IOException {
        return getVacationData().get(7);
    }

    public static VacationDataDTO getCorrectHolidays() throws IOException {
        return getVacationData().get(8);
    }
}
