package br.com.odontoapp.schedule.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HolidayResponse {

    private String date;
    private String name;
    private String type;
    private String level;

}
