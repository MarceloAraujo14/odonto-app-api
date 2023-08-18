package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.constants.ScheduleConstants;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class ValidateTime implements Executor<Schedule> {

    private final Loggr log = new Loggr(ValidateTime.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateTime(input.getTimes());
        return input;
    }

    private void validateTime(List<LocalTime> times){
        log.event().state(ProcessState.PROCESSING).m("validateTime").param("times", times).info();

        if(times.isEmpty()) throw new IllegalArgumentException("Escolha pelo menos um horário para agendar.");

        times.forEach(time -> {
            if(!ScheduleConstants.TIME_BLOCK.contains(time.toString())){
                throw new IllegalArgumentException(String.format("Horário %sh escolhido é inválido.", time));
            }
        });
    }
}
