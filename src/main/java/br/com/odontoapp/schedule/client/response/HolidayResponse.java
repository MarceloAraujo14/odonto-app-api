package br.com.odontoapp.schedule.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class HolidayResponse {

    private String date;
    private String name;
    private String type;
    private String level;

}
