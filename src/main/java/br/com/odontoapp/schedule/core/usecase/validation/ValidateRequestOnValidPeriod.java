package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ValidateRequestOnValidPeriod implements Executor<Schedule> {

    private final Loggr log = new Loggr(ValidateRequestOnValidPeriod.class.getName());

    @Override
    public Schedule execute(Schedule input) {
        validateAfterNow(input.getDate(), input.getBeginAt());
        return input;
    }

    private void validateAfterNow(LocalDate date, LocalTime beginAt){
        log.event().state(ProcessState.PROCESSING).m("validateAfterNow").param("date", date).param("beginAt", beginAt).info();
        if(date.atTime(beginAt).isBefore(LocalDateTime.now().plusHours(1))){
            throw new IllegalArgumentException("Não é possível agendar horários com menos de 1 hora de antecedência.");
        }
    }
}
