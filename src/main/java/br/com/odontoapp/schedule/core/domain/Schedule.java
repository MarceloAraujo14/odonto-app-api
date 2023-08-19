package br.com.odontoapp.schedule.core.domain;

import br.com.odontoapp.schedule.core.enums.ScheduleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule {

    @Id
    @Column(name = "schedule_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "times")
    private Set<LocalTime> times;
    @Column(name = "services")
    private List<String> services;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ScheduleStatus status;
    @Column(name = "doctor")
    private String doctor;

    public LocalTime getBeginAt(){
        return this.times.iterator().next();
    }

}
