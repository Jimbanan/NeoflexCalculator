package com.example.neoflex.service;

import com.example.neoflex.dto.CalendarHolidayDTO;
import com.example.neoflex.dto.VacationDataDTO;

import java.io.IOException;
import java.math.BigDecimal;

public interface CalculatorSalaryService {

    BigDecimal vacationPayCalculator(VacationDataDTO vacationDataDTO) throws Exception;

    Integer workingDayCalculation(CalendarHolidayDTO jsonCalendar, VacationDataDTO vacationDataDTO);

    CalendarHolidayDTO createJsonCalendar();

    void downloadJsonCalendar(String urlStr, String file) throws IOException;

}
