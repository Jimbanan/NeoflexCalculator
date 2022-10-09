package com.example.neoflex.test_data;

import com.example.neoflex.dto.VacationDataDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CalculationTestDataInController {

    static String jsonPath = "src/main/resources/testParamsInController.json";

    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private static List<VacationDataDTO> getVacationData() throws IOException {
        return objectMapper.readValue(new File(jsonPath), new TypeReference<>() {
        });
    }

    public static VacationDataDTO getCorrectTest()throws IOException{
        return getVacationData().get(0);
    }

    public static VacationDataDTO getCorrectZeroYearSalary()throws IOException{
        return getVacationData().get(1);
    }

    public static VacationDataDTO getCorrectZeroNumberOfVacationDays()throws IOException{
        return getVacationData().get(2);
    }

    public static VacationDataDTO getWrongStartVacationMoreEndVacation()throws IOException{
        return getVacationData().get(3);
    }

}