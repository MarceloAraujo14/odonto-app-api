package br.com.odontoapp.schedule.repository;

import br.com.odontoapp.schedule.core.domain.ScheduleDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ScheduleDateTimeRepository extends JpaRepository<ScheduleDateTime, LocalDate> {

}
