package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidateRequestOnValidPeriod implements Executor<Schedule> {

    private final Loggr log = new Loggr(ValidateRequestOnValidPeriod.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateAfterNow(input);
        return input;
    }

    private void validateAfterNow(Schedule input){
        log.event().state(ProcessState.PROCESSING).m("validateAfterNow").param("date", input.getDate()).param("beginAt", input.getBeginAt()).info();
        if(input.getDate().atTime(input.getBeginAt()).isBefore(LocalDateTime.now().plusHours(1))){
            input.setStatus(ScheduleStatus.FALHA);
            throw new IllegalArgumentException("Não é possível agendar horários com menos de 1 hora de antecedência.");
        }
    }
}
