package br.com.odontoapp.schedule.api;

import br.com.odontoapp.schedule.api.mapper.ScheduleMapper;
import br.com.odontoapp.schedule.api.request.ScheduleRequest;
import br.com.odontoapp.schedule.api.response.ScheduleResponse;
import br.com.odontoapp.schedule.core.model.Schedule;
import br.com.odontoapp.schedule.core.usecase.CreateScheduleUseCase;
import br.com.odontoapp.schedule.repository.ScheduleRepository;
import br.com.odontoapp.shared.Loggr;
import br.com.odontoapp.shared.ProcessState;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static br.com.odontoapp.schedule.api.mapper.ScheduleMapper.requestToSchedule;
import static br.com.odontoapp.schedule.api.mapper.ScheduleMapper.scheduleToResponse;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleResource {

    private final CreateScheduleUseCase createScheduleUseCase;
    private final ScheduleRepository scheduleRepository;
    private final Loggr log = new Loggr(ScheduleResource.class.getName());

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request){
        log.state(ProcessState.NEW).m("createSchedule").param("ScheduleRequest", request).info();
        Schedule schedule = requestToSchedule(request);
        Schedule response = createScheduleUseCase.execute(schedule);
        ScheduleResponse responseMap = scheduleToResponse(response);
        log.event().state(ProcessState.SUCCESS).m("createSchedule").param("ScheduleResponse", responseMap).info();
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getScheduleByDate(@PathParam("date") String date){
        log.state(ProcessState.NEW).m("getScheduleByDate").param("date", date).info();

        List<ScheduleResponse> response = scheduleRepository.findByDate(
                LocalDate.parse(date)).stream()
                .map(ScheduleMapper::scheduleToResponse).toList();

        log.state(ProcessState.SUCCESS).param("response", response).info();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleResponse getScheduleById(@PathVariable("scheduleId") Integer scheduleId){
        return ScheduleResponse.builder().build();
    }

    @PatchMapping("/{scheduleId}")
    public ScheduleResponse updateSchedule(@RequestBody ScheduleRequest request, @PathVariable("scheduleId") Integer scheduleId){
        return ScheduleResponse.builder().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ScheduleResponse cancelSchedule(@PathVariable("scheduleId") Integer scheduleId){
        return ScheduleResponse.builder().build();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleResponse> getSchedulesByCustomer(@PathVariable("customerId") Integer customerId){
        return List.of(ScheduleResponse.builder().build());
    }

}
