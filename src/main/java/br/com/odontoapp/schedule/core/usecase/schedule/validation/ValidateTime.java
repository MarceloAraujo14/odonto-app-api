package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.constants.ScheduleConstants;
import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import org.springframework.stereotype.Component;

@Component
public class ValidateTime implements Executor<Schedule> {

    private final Loggr log = new Loggr(ValidateTime.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateTime(input);
        return input;
    }

    private void validateTime(Schedule input){
        log.event().state(ProcessState.PROCESSING).m("validateTime").param("times", input.getTimes()).info();

        if(input.getTimes().isEmpty()) {
            input.setStatus(ScheduleStatus.FALHA);
            throw new IllegalArgumentException("Escolha pelo menos um horário para agendar.");
        }

        input.getTimes().forEach(time -> {
            if(!ScheduleConstants.TIME_BLOCK.contains(time.toString())){
                input.setStatus(ScheduleStatus.FALHA);
                throw new IllegalArgumentException(String.format("Horário %sh escolhido é inválido.", time));
            }
        });
    }
}
