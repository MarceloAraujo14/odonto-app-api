package br.com.odontoapp.schedule.client;

import br.com.odontoapp.schedule.client.response.HolidayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "holiday", url = "${holiday-api.url}")
public interface HolidayApiClient {

    @GetMapping(path = "/{year}")
    List<HolidayResponse> findHoliday(@PathVariable("year")Integer year, @RequestParam("state") String state, @RequestParam("token") String token);

}
