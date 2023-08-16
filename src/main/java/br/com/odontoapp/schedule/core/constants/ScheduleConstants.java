package br.com.odontoapp.schedule.core.constants;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface ScheduleConstants {

    List<String> TIME_BLOCK = List.of(
            "9:00", "9:30", "10:00", "10:30", "11:00", "11:30",
            "13:00", "13:30", "14:00", "14:30","15:00",
            "15:30","16:00", "16:30", "17:00", "17:30");

    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}
