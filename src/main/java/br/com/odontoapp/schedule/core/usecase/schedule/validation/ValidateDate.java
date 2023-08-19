package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import org.springframework.stereotype.Component;

@Component
public class ValidateDate implements Executor<Schedule> {

    private final Loggr log = new Loggr(ValidateDate.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateDate(input);
        return input;
    }

    private void validateDate(Schedule input){
        log.event().state(ProcessState.PROCESSING).m("validateDate").param("date", input.getDate()).info();
        if(input.getDate().getDayOfWeek().getValue() == 7){
            input.setStatus(ScheduleStatus.FALHA);
            throw new IllegalArgumentException("Não é possível agendar horários aos domingos.");
        }
    }
}
