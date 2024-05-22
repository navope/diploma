package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.AlertDTO;
import navope.rento.computingUnit.dto.MeasurementDTO;

import navope.rento.computingUnit.services.PressureService;
import navope.rento.computingUnit.services.TemperatureService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/measurement")
//@RequiredArgsConstructor
//public class MeasurementController {
//    private final ModelMapper modelMapper;
//    private final PressureService pressureService;
//    private final TemperatureService temperatureService;
//    private final SimpMessagingTemplate template;
//
//    @PostMapping("/new")
//    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody MeasurementDTO measurementDTO) {
//        template.convertAndSend("/topic/measurements", measurementDTO);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//}
@RestController
@RequiredArgsConstructor
@RequestMapping("/measurement")
public class MeasurementController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> receiveMeasurement(@RequestBody MeasurementDTO measurement) {
        double pressure = measurement.getPressureDTO().getValue();
        double temperature = measurement.getTemperatureDTO().getValue();

        final double PRESSURE_MIN = 60000;
        final double PRESSURE_MAX = 115000;
        final double TEMPERATURE_MIN = 10;
        final double TEMPERATURE_MAX = 28;

        boolean pressureAlert = pressure < PRESSURE_MIN || pressure > PRESSURE_MAX;
        boolean temperatureAlert = temperature < TEMPERATURE_MIN || temperature > TEMPERATURE_MAX;

        AlertDTO alert = new AlertDTO();
        alert.setPressure(pressure);
        alert.setTemperature(temperature);
        alert.setPressureAlert(pressureAlert);
        alert.setTemperatureAlert(temperatureAlert);

        if (pressureAlert && temperatureAlert) {
            alert.setMessage("Предупреждение! Превышены пороговые значения давления и температуры!");
        } else if (pressureAlert) {
            alert.setMessage("Предупреждение! Превышен порог давления!");
        } else if (temperatureAlert) {
            alert.setMessage("Предупреждение! Превышен порог температуры!");
        } else {
            alert.setMessage("Показания в норме.");
        }

        messagingTemplate.convertAndSend("/topic/alerts", alert);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
