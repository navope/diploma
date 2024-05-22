package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.AlertDTO;
import navope.rento.computingUnit.dto.MeasurementDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurement")
public class MeasurementController {
    private Boolean isMonitoring = false;
    private Boolean isStopReading = false;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> receiveMeasurement(@RequestBody MeasurementDTO measurement) {
        double pressure = measurement.getPressureDTO().getValue();
        double temperature = measurement.getTemperatureDTO().getValue();

        if (isMonitoring) {
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
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<HttpStatus> startMonitoring() {
        isMonitoring = true;
        isStopReading = false;
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<HttpStatus> stopMonitoring() {
        isMonitoring = false;
        isStopReading = true;
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/monitoring_state")
    public ResponseEntity<Boolean> monitoringState() {
        return ResponseEntity.ok(isStopReading);
    }
}
