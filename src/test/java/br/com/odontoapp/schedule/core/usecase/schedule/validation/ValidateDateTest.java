package br.com.odontoapp.schedule.core.usecase.schedule.validation;

import br.com.odontoapp.schedule.core.domain.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidateDateTest {

    @InjectMocks
    ValidateDate validateDate;

    @Test
    void valid_date_test(){
        var input = buildInput(LocalDate.now());
        assertDoesNotThrow(() -> validateDate.execute(input));
    }

    @Test
    void invalid_date_request_sunday(){
        var input = buildInput(LocalDate.parse("2023-08-20"));
        assertThrows(IllegalArgumentException.class, ()-> validateDate.execute(input));
    }

    private Schedule buildInput(LocalDate date){
        return Schedule.builder()
                .date(date)
                .build();
    }
}