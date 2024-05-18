package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.SensorsDataDTO;
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
    @SendTo("topic/sensorsData")
    public SensorsDataDTO addNewData(SensorsDataDTO sensorsDataDTO) {
        System.out.println("sd");
        return sensorsDataDTO;
    }
}
