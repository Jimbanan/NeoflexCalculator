package com.example.neoflex.controller;

import com.example.neoflex.dto.VacationDataDTO;
import com.example.neoflex.test_data.CalculationTestDataInController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CalculateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCalculateCorrectData() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInController.getCorrectTest();

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("yearSalary", vacationDataDTOTest.getYearSalary().toString());
        requestParams.add("numberOfVacationDays", vacationDataDTOTest.getNumberOfVacationDays().toString());
        requestParams.add("startVacation", vacationDataDTOTest.getStartVacation().toString());
        requestParams.add("endVacation", vacationDataDTOTest.getEndVacation().toString());

        mockMvc.perform(get("/calculate")
                        .params(requestParams))
                .andExpect(status().isOk());
    }


    @Test
    void getCorrectZeroYearSalary() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInController.getCorrectZeroYearSalary();

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("yearSalary", vacationDataDTOTest.getYearSalary().toString());
        requestParams.add("numberOfVacationDays", vacationDataDTOTest.getNumberOfVacationDays().toString());
        requestParams.add("startVacation", vacationDataDTOTest.getStartVacation().toString());
        requestParams.add("endVacation", vacationDataDTOTest.getEndVacation().toString());

        mockMvc.perform(get("/calculate")
                        .params(requestParams))
                .andExpect(status().isOk());

    }

    @Test
    void getCorrectZeroNumberOfVacationDays() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInController.getCorrectZeroNumberOfVacationDays();

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("yearSalary", vacationDataDTOTest.getYearSalary().toString());
        requestParams.add("numberOfVacationDays", vacationDataDTOTest.getNumberOfVacationDays().toString());
        requestParams.add("startVacation", vacationDataDTOTest.getStartVacation().toString());
        requestParams.add("endVacation", vacationDataDTOTest.getEndVacation().toString());

        mockMvc.perform(get("/calculate")
                        .params(requestParams))
                .andExpect(status().isOk());

    }

    @Test
    void getWrongStartVacationMoreEndVacation() throws Exception {

        VacationDataDTO vacationDataDTOTest = CalculationTestDataInController.getWrongStartVacationMoreEndVacation();

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("yearSalary", vacationDataDTOTest.getYearSalary().toString());
        requestParams.add("numberOfVacationDays", vacationDataDTOTest.getNumberOfVacationDays().toString());
        requestParams.add("startVacation", vacationDataDTOTest.getStartVacation().toString());
        requestParams.add("endVacation", vacationDataDTOTest.getEndVacation().toString());

        mockMvc.perform(get("/calculate")
                        .params(requestParams))
                .andExpect(status().is4xxClientError());

    }

}