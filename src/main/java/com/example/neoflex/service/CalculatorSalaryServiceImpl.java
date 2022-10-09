package com.example.neoflex.service;

import com.example.neoflex.dto.CalendarHolidayDTO;
import com.example.neoflex.dto.VacationDataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class CalculatorSalaryServiceImpl implements CalculatorSalaryService {

    @Value("${calendar.url}")
    private String FILE_URL;

    @Value("${file.location}")
    private String FILE_LOCATION;

    public BigDecimal vacationPayCalculator(VacationDataDTO vacationDataDTO) throws Exception {

        if (vacationDataDTO.getYearSalary() == null && vacationDataDTO.getNumberOfVacationDays() == null)
            throw new NullPointerException("YearSalary и NumberOfVacationDays не имеют значений!");
        if (vacationDataDTO.getYearSalary() == null)
            throw new NullPointerException("YearSalary не имеет значение!");
        if (vacationDataDTO.getNumberOfVacationDays() == null)
            throw new NullPointerException("NumberOfVacationDays не имеет значение!");

        CalendarHolidayDTO jsonCalendar = createJsonCalendar();//получаем календарь праздников и выходных

        Integer daysOfVacation;
        if (vacationDataDTO.getStartVacation() != null || vacationDataDTO.getEndVacation() != null) {
            if (Period.between(LocalDate.parse(vacationDataDTO.getStartVacation().toString()), LocalDate.parse(vacationDataDTO.getEndVacation().toString())).getDays() < 0)
                throw new Exception("StartVacation больше EndVacation!");
            else {
                daysOfVacation = workingDayCalculation(jsonCalendar, vacationDataDTO);
                log.trace("vacationPayCalculator: получено количество дней в отпуске на основе введенной даты начала и даты окончания отпуска: " + daysOfVacation);
            }
        } else {
            daysOfVacation = vacationDataDTO.getNumberOfVacationDays();
            log.trace("vacationPayCalculator: получено количество дней в отпуске без даты начала и даты окончания отпуска: " + daysOfVacation);
        }

        BigDecimal salary = BigDecimal.valueOf((vacationDataDTO.getYearSalary() / 12));//Рассчет ежемесячной зарплаты
        log.trace("vacationPayCalculator: расчитана ежемесячная зарплата: " + salary);

        BigDecimal averageSalary = salary.divide(BigDecimal.valueOf(29.3), RoundingMode.DOWN);//Деление ежемесячной зарплаты на среднее число дней в месяце
        log.trace("vacationPayCalculator: расчитана ежемесячная зарплата на основе среднего количества дней в месяце: " + averageSalary);

        BigDecimal vacation = averageSalary.multiply(BigDecimal.valueOf(daysOfVacation));//Рассчет отпускных
        log.trace("vacationPayCalculator: расчитана сумма отпускных: " + vacation);

        return vacation;
    }

    public Integer workingDayCalculation(CalendarHolidayDTO jsonCalendar, VacationDataDTO vacationDataDTO) {

        Integer daysOfVacation = Math.toIntExact(ChronoUnit.DAYS.between(vacationDataDTO.getStartVacation(), vacationDataDTO.getEndVacation()));
        log.trace("workingDayCalculation: расчитано количество дней в отпуске: " + daysOfVacation);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        Integer countHolidays = 0;
        LocalDate parseDate;
        LocalDate startDate = vacationDataDTO.getStartVacation();

        for (String holiday : jsonCalendar.getHolidays()) {
            parseDate = LocalDate.parse(holiday, formatter);
            for (int i = 0; i <= daysOfVacation; i++) {
                if (parseDate.isAfter(startDate.minusDays(1)) && parseDate.isBefore(vacationDataDTO.getEndVacation().plusDays(1))) {
                    if (parseDate.toString().equals(startDate.toString())) {
                        if (!parseDate.getDayOfWeek().toString().equals("SATURDAY") && !parseDate.getDayOfWeek().toString().equals("SUNDAY")) {
                            countHolidays++;
                            System.out.println(parseDate);
                        }
                    }
                }
                startDate = startDate.plusDays(1);
            }
            startDate = vacationDataDTO.getStartVacation();
        }

        log.trace("workingDayCalculation: расчитано количество дней в отпуске с учетом праздников (без оплаты): " + (daysOfVacation - countHolidays) + 1);

        return (daysOfVacation - countHolidays) + 1;
    }

    public CalendarHolidayDTO createJsonCalendar() {

        ObjectMapper objectMapper = new ObjectMapper();

        CalendarHolidayDTO calendarHolidayDTO;
        try {
            downloadJsonCalendar(FILE_URL, FILE_LOCATION);
            log.trace("createJsonCalendar: скачан json файл с адреса: " + FILE_URL + " по пути: " + FILE_LOCATION);

            calendarHolidayDTO = objectMapper.readValue(new File(FILE_LOCATION), CalendarHolidayDTO.class);
            log.trace("createJsonCalendar: на основе скачанного json файла создан объект класса CalendarHolidayDTO: " + calendarHolidayDTO);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return calendarHolidayDTO;
    }

    public void downloadJsonCalendar(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        log.trace("downloadJsonCalendar: создан объект класса URL на основе переданной ссылки: " + urlStr);

        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        log.trace("downloadJsonCalendar: создан объект класса ReadableByteChannel на основе открытокго потока url");


        FileOutputStream fos = new FileOutputStream(file);
        log.trace("downloadJsonCalendar: создан объект класса FileOutputStream на основе местоположения создаваемого файла: " + file);

        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        fos.close();
        rbc.close();
    }

}
