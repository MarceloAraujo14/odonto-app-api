package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.domain.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidateBeginAtPeriodTest {

    @InjectMocks
    ValidateBeginAtPeriod validateAfterLimitTimeRequest;

    @Test
    void should_do_nothing_when_request_time_is_after_1_hour_from_now(){
        var input = buildScheduleInput(LocalDate.now(), LocalTime.now().plusMinutes(61));
        assertDoesNotThrow(() -> validateAfterLimitTimeRequest.execute(input));
    }

    @Test
    void should_throw_when_request_time_begin_before_1_hour_from_now(){
        var input = buildScheduleInput(LocalDate.now(), LocalTime.now().plusMinutes(59));
        assertThrows(IllegalArgumentException.class, () -> validateAfterLimitTimeRequest.execute(input));
    }

    private Schedule buildScheduleInput(LocalDate date, LocalTime beginAt){
        return Schedule.builder().date(date).times(Set.of(beginAt)).build();
    }

}