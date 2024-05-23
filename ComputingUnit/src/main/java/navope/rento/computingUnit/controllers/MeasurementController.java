package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.MeasurementDTO;

import navope.rento.computingUnit.services.MeasurementService;
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
    private final MeasurementService measurementService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> receiveMeasurement(@RequestBody MeasurementDTO measurement) {
        double pressure = measurement.getPressure();
        double temperature = measurement.getTemperature();

        if (isMonitoring) {
            measurementService.saveMeasurement(temperature, pressure);
            messagingTemplate.convertAndSend("/topic/alerts",
                    measurementService.analysisMeasurement(temperature,pressure));
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
