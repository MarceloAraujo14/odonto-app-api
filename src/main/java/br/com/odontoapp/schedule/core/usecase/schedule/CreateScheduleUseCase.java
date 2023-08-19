package br.com.odontoapp.schedule.core.usecase.schedule;

import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.domain.Schedule;
import br.com.odontoapp.schedule.core.domain.ScheduleDateTime;
import br.com.odontoapp.schedule.core.usecase.chain.ExecutorChain;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateHoliday;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateBeginAtPeriod;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateDate;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateOverlapTime;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateTime;
import br.com.odontoapp.schedule.repository.ScheduleDateTimeRepository;
import br.com.odontoapp.schedule.repository.ScheduleRepository;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreateScheduleUseCase {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDateTimeRepository dateTimeRepository;
    private final ValidateDate validateDate;
    private final ValidateBeginAtPeriod validateAfterLimitTimeRequest;
    private final ValidateTime validateTime;
    private final ValidateOverlapTime validateOverlapTime;
    private final ValidateHoliday validateHoliday;
    private final Loggr log = new Loggr(CreateScheduleUseCase.class.getName());

    public Schedule execute(Schedule schedule){
        log.state(ProcessState.NEW).m("execute").param("schedule", schedule).info();

        validate().execute(schedule);

        schedule.setStatus(ScheduleStatus.AGENDADO);
        updateDateTime(schedule);
        Schedule scheduleSaved = scheduleRepository.save(schedule);
        log.event().state(ProcessState.SUCCESS).m("execute").param("scheduleSaved", scheduleSaved).info();
        return scheduleSaved;
    }

    private ExecutorChain<Schedule> validate(){
        return ExecutorChain.<Schedule>builder()
                .chain(validateDate)
                .chain(validateHoliday)
                .chain(validateTime)
                .chain(validateAfterLimitTimeRequest)
                .chain(validateOverlapTime)
                .build();
    }

    private void updateDateTime(Schedule schedule){
        dateTimeRepository.save(
                ScheduleDateTime.builder()
                        .date(schedule.getDate())
                        .unavailableTimes(schedule.getTimes()).build());
    }

}
