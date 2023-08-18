package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.schedule.repository.ScheduleDateTimeRepository;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidateOverlapTime implements Executor<Schedule> {

    private final ScheduleDateTimeRepository dateTimeRepository;
    private final Loggr log = new Loggr(ValidateOverlapTime.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateOverlapTime(input.getDate(), input.getTimes());
        return input;
    }

    private void validateOverlapTime(LocalDate date, List<LocalTime> times){
        log.event().state(ProcessState.PROCESSING).m("validateOverlapTime").param("date", date).param("times", times).info();
        var unavailableTimes = dateTimeRepository.findById(date);
        log.param("unavailableTimes", unavailableTimes).info();
        unavailableTimes.ifPresent(scheduleDateTime -> times.forEach(time -> {
            if (scheduleDateTime.getUnavailableTimes().contains(time)) {
                throw new IllegalArgumentException("O horário escolhido já está agendado.");
            }
        }));
    }
}
