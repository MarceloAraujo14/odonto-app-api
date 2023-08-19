package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.api.ScheduleResource;
import br.com.odontoapp.schedule.client.HolidayApiClient;
import br.com.odontoapp.schedule.client.response.HolidayResponse;
import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.chain.Executor;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidateHoliday implements Executor<Schedule> {

    private final HolidayApiClient holidayApiClient;

    private final Loggr log = new Loggr(ValidateHoliday.class.getName());

    @Value("${holiday-api.token}")
    private String token;

    @Override
    public Schedule execute(Schedule input) {
        log.state(ProcessState.NEW).m("execute").param("input", input).info();
        validateHoliday(input);
        return input;
    }

    public void validateHoliday(Schedule input){
        List<HolidayResponse> holidays = holidayApiClient.findHoliday(LocalDate.now().getYear(), "RJ", token);
        holidays.forEach(holiday -> {
            if(holiday.getDate().equals(input.getDate().toString())){
                input.setStatus(ScheduleStatus.FALHA);
                throw new IllegalArgumentException("A data escolhida é feriado, favor informe outra data.");
            }
        });
    }
}
