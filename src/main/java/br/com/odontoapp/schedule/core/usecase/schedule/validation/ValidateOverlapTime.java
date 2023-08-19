package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.domain.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.schedule.repository.ScheduleDateTimeRepository;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateOverlapTime implements Executor<Schedule> {

    private final ScheduleDateTimeRepository dateTimeRepository;
    private final Loggr log = new Loggr(ValidateOverlapTime.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateOverlapTime(input);
        return input;
    }

    private void validateOverlapTime(Schedule input){
        log.event().state(ProcessState.PROCESSING).m("validateOverlapTime").param("date", input.getDate()).param("times", input.getTimes()).info();
        var unavailableTimes = dateTimeRepository.findById(input.getDate());
        log.param("unavailableTimes", unavailableTimes).info();
        unavailableTimes.ifPresent(scheduleDateTime -> input.getTimes().forEach(time -> {
            if (scheduleDateTime.getUnavailableTimes().contains(time)) {
                input.setStatus(ScheduleStatus.FALHA);
                throw new IllegalArgumentException("O horário escolhido já está agendado.");
            }
        }));
    }
}
