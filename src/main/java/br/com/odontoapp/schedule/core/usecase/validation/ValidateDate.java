package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateDate implements Executor<Schedule> {

    private final Loggr log = new Loggr(ValidateDate.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateDate(input.getDate());
        return input;
    }

    private void validateDate(LocalDate date){
        log.event().state(ProcessState.PROCESSING).m("validateDate").param("date", date).info();
        if(date.getDayOfWeek().getValue() == 7){
            throw new IllegalArgumentException("Não é possível agendar horários aos domingos.");
        }
        //todo: validate holiday
    }
}
