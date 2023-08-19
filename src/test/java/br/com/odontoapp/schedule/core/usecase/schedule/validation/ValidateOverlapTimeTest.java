package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.domain.Schedule;
import br.com.odontoapp.schedule.core.domain.ScheduleDateTime;
import br.com.odontoapp.schedule.repository.ScheduleDateTimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateOverlapTimeTest {

    private static final LocalDate DATE = LocalDate.parse("2023-08-18");
    public static final LocalTime TIME = LocalTime.parse("10:30");
    public static final LocalDate DATE1 = LocalDate.parse("2023-08-19");
    @InjectMocks
    ValidateOverlapTime validateOverlapTime;

    @Mock
    ScheduleDateTimeRepository dateTimeRepository;

    @Test
    void valid_request_time_test(){
        var input = buildScheduleInput(DATE, Set.of(TIME));
        when(dateTimeRepository.findById(DATE)).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> validateOverlapTime.execute(input));
        verify(dateTimeRepository, times(1)).findById(DATE);
    }

    @Test
    void throws_when_request_time_already_taken_test(){
        var input = buildScheduleInput(DATE, Set.of(TIME));
        when(dateTimeRepository.findById(DATE)).thenReturn(Optional.of(ScheduleDateTime.builder().date(DATE).unavailableTimes(Set.of(TIME)).build()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateOverlapTime.execute(input));
        verify(dateTimeRepository, times(1)).findById(DATE);
    }

    @Test
    void do_nothing_when_request_time_is_diferent_test(){
        var input = buildScheduleInput(DATE1, Set.of(TIME));
        when(dateTimeRepository.findById(DATE1)).thenReturn(Optional.of(ScheduleDateTime.builder().date(DATE1).unavailableTimes(Set.of(LocalTime.parse("11:30"))).build()));
        Assertions.assertDoesNotThrow(() -> validateOverlapTime.execute(input));
        verify(dateTimeRepository, times(1)).findById(DATE1);
    }


    private Schedule buildScheduleInput(LocalDate date, Set<LocalTime> times){
        return Schedule.builder()
                .date(date)
                .times(times)
                .build();
    }

}