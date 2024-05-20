package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.MeasurementDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurement")
@RequiredArgsConstructor
public class MeasurementController {
    private final ModelMapper modelMapper;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        System.out.println(measurementDTO.getPressureDTO().getValue());
        System.out.println(measurementDTO.getTemperatureDTO().getValue());
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
