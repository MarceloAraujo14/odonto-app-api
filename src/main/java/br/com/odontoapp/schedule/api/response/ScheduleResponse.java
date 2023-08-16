package br.com.odontoapp.schedule.api.response;

import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import br.com.odontoapp.schedule.core.enums.ServiceType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class ScheduleResponse {

    private Integer id;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private String date;
    private LocalTime beginAt;
    private int duration;
    private List<ServiceType> services;
    private ScheduleStatus status;
}
