package br.com.odontoapp.schedule.api.request;

import br.com.odontoapp.schedule.core.enums.ServiceType;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;
import java.util.TreeSet;

@Data
@Builder
public class ScheduleRequest {
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    @DateTimeFormat(pattern = "dd/MM/yyyy", fallbackPatterns = "yyyy/MM/dd")
    private String date;
    private TreeSet<LocalTime> times;
    private List<ServiceType> services;
    private String doctor;
}
