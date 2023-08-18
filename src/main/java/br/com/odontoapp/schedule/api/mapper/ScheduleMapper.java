package br.com.odontoapp.schedule.api.mapper;

import br.com.odontoapp.schedule.api.request.ScheduleRequest;
import br.com.odontoapp.schedule.api.response.ScheduleResponse;
import br.com.odontoapp.schedule.core.enums.ServiceType;
import br.com.odontoapp.schedule.core.model.Schedule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static br.com.odontoapp.schedule.core.constants.ScheduleConstants.DATE_FORMATTER;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleMapper {

    public static Schedule requestToSchedule(ScheduleRequest request){
        return Schedule.builder()
                .doctor(request.getDoctor())
                .customerId(request.getCustomerId())
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .date(LocalDate.parse(request.getDate(), DATE_FORMATTER))
                .times(request.getTimes())
                .services(request.getServices().stream().map(Enum::toString).toList())
                .build();
    }

    public static ScheduleResponse scheduleToResponse(Schedule schedule){
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .doctor(schedule.getDoctor())
                .customerId(schedule.getCustomerId())
                .customerName(schedule.getCustomerName())
                .customerPhone(schedule.getCustomerPhone())
                .date(schedule.getDate().toString())
                .beginAt(schedule.getTimes().get(0))
                .services(schedule.getServices().stream().map(ServiceType::valueOf).toList())
                .status(schedule.getStatus())
                .build();
    }
}
