package br.com.odontoapp.schedule.core.usecase.validation;

import br.com.odontoapp.schedule.core.model.Schedule;
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

    }

    private Schedule buildInput(LocalDate date){
        return Schedule.builder()
                .date(date)
                .build();
    }
}