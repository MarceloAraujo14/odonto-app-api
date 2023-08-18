package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.schedule.validation.ValidateDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateDateTest {

    @InjectMocks
    ValidateDate validateDate;

    @Test
    void valid_date_test(){
        var input = buildInput(LocalDate.now());
        assertDoesNotThrow(() -> validateDate.execute(input));
    }

    void invalid_date_request_sunday(){

    }

    private Schedule buildInput(LocalDate date){
        return Schedule.builder()
                .date(date)
                .build();
    }
}