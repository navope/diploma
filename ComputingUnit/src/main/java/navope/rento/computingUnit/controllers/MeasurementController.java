package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.MeasurementDTO;

import navope.rento.computingUnit.services.PressureService;
import navope.rento.computingUnit.services.TemperatureService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measurement")
@RequiredArgsConstructor
public class MeasurementController {
    private final ModelMapper modelMapper;
    private final PressureService pressureService;
    private final TemperatureService temperatureService;
    private MeasurementDTO latestMeasurement;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        latestMeasurement = measurementDTO;
        System.out.println(latestMeasurement.getPressureDTO().getValue());
        System.out.println(latestMeasurement.getTemperatureDTO().getValue());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/display")
    public String display(Model model) {
//        Pressure pressure = PressureService.getLastPressure();
//        Temperature temperature = temperatureService.getLastTemperature();
//        MeasurementDTO measurementDTO = MeasurementDTO.builder()
//                .pressureDTO(PressureDTO.builder()
//                        .receivedAt()
//                        .build())
//                .temperatureDTO(TemperatureDTO.builder()
//                        .build())
//                .build();
        model.addAttribute("measurement", latestMeasurement);
        return "templates/display";
    }

    @GetMapping("/latest-measurement")
    @ResponseBody
    public MeasurementDTO getLatestMeasurement() {
        return latestMeasurement;
    }

}
