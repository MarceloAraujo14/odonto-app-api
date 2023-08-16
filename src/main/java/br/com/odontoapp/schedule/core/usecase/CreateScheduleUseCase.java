package br.com.odontoapp.schedule.core.usecase;

import br.com.odontoapp.schedule.core.constants.ScheduleConstants;
import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.model.ScheduleDateTime;
import br.com.odontoapp.schedule.repository.ScheduleDateTimeRepository;
import br.com.odontoapp.schedule.repository.ScheduleRepository;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateScheduleUseCase {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDateTimeRepository dateTimeRepository;
    private final Loggr log = new Loggr(CreateScheduleUseCase.class.getName());

    @Transactional
    public Schedule execute(Schedule schedule){
        log.state(ProcessState.NEW).m("execute").param("schedule", schedule).info();
        validate(schedule);
        schedule.setStatus(ScheduleStatus.AGENDADO);
        updateDateTime(schedule);
        log.state(ProcessState.SUCCESS).info();
        return scheduleRepository.save(schedule);
    }

    private void validate(Schedule schedule){
        validateDate(schedule.getDate());
        validateAfterNow(schedule.getDate(), LocalTime.parse(schedule.getTimes().get(0)));
        validateTime(schedule.getTimes());
        validateOverlapTime(schedule.getDate(), schedule.getTimes());
    }

    private void validateDate(LocalDate date){
        log.event().state(ProcessState.PROCESSING).m("validateDate").param("date", date).info();
        if(date.getDayOfWeek().getValue() == 7){
            throw new IllegalArgumentException("Não é possível agendar horários aos domingos.");
        }
        //todo: validate holiday
    }

    private void validateAfterNow(LocalDate date, LocalTime beginAt){
        log.event().state(ProcessState.PROCESSING).m("validateAfterNow").param("date", date).param("beginAt", beginAt).info();
        if(date.atTime(beginAt).isAfter(LocalDateTime.now().plusHours(1))){
            throw new IllegalArgumentException("Não é possível agendar horários com menos de 1 hora de antecedência.");
        }
    }

    private void validateTime(List<String> times){
        log.event().state(ProcessState.PROCESSING).m("validateTime").param("times", times).info();
        times.forEach(time -> {
            if(!ScheduleConstants.TIME_BLOCK.contains(time)){
                throw new IllegalArgumentException(String.format("Horário %sh escolhido é inválido.", time));
            }
        });
    }

    private void validateOverlapTime(LocalDate date, List<String> times){
        log.event().state(ProcessState.PROCESSING).m("validateOverlapTime").param("date", date).param("times", times).info();
        var unavailableTimes = dateTimeRepository.findById(date);
        log.param("unavailableTimes", unavailableTimes).info();
        unavailableTimes.ifPresent(scheduleDateTime -> times.forEach(time -> {
            if (scheduleDateTime.getUnavailableTimes().contains(time)) {
                throw new IllegalArgumentException("O horário escolhido já está agendado.");
            }
        }));
    }

    private void updateDateTime(Schedule schedule){
        dateTimeRepository.save(ScheduleDateTime.builder().date(schedule.getDate()).unavailableTimes(schedule.getTimes()).build());
    }

}
