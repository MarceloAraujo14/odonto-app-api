package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.domain.Schedule;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateOverlapTime;
import br.com.odontoapp.schedule.repository.ScheduleDateTimeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ValidateOverlapTimeTest {

    @InjectMocks
    ValidateOverlapTime validateOverlapTime;

    @Mock
    ScheduleDateTimeRepository dateTimeRepository;


    private Schedule buildScheduleInput(LocalDate date, Set<LocalTime> times){
        return Schedule.builder()
                .date(date)
                .times(times)
                .build();
    }

}