package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.SensorDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sensor_readings")
@RequiredArgsConstructor
public class SensorReadingsController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/new")
    @SendTo("topic/interface")
    public SensorDataDTO addNewData(@RequestBody SensorDataDTO sensorDataDTO) {
        System.out.println("--------------------------------------------------");
        System.out.println("pressure - " + sensorDataDTO.getPressureDTO().getValue());
        System.out.println("temperature - " + sensorDataDTO.getTemperatureDTO().getValue());
        System.out.println("--------------------------------------------------\n");
        simpMessagingTemplate.convertAndSend("/topic/sensor-data", sensorDataDTO);
        return null;
    }
}
