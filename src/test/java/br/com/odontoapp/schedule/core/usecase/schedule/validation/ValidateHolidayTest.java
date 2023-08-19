package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.client.HolidayApiClient;
import br.com.odontoapp.schedule.client.response.HolidayResponse;
import br.com.odontoapp.schedule.core.domain.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateHolidayTest {

    @InjectMocks
    ValidateHoliday validateHoliday;

    @Mock
    HolidayApiClient holidayApiClient;

    @Test
    void do_nothing_when_request_date_not_holiday_test(){
        var input = buildScheduleInput(LocalDate.parse("2023-08-18"));
        when(holidayApiClient.findHoliday(any(), any(), any())).thenReturn(Collections.emptyList());
        assertDoesNotThrow(() -> validateHoliday.execute(input));
        verify(holidayApiClient, times(1)).findHoliday(any(), any(), any());
    }

    @Test
    void throw_when_request_date_is_holiday_test(){
        var input = buildScheduleInput(LocalDate.parse("2023-12-25"));
        when(holidayApiClient.findHoliday(any(), any(), any())).thenReturn(List.of(HolidayResponse.builder().date("2023-12-25").build()));
        assertThrows(IllegalArgumentException.class, () -> validateHoliday.execute(input));
        verify(holidayApiClient, times(1)).findHoliday(any(), any(), any());
    }

    private Schedule buildScheduleInput(LocalDate date){
        return Schedule.builder()
                .date(date)
                .build();
    }
}