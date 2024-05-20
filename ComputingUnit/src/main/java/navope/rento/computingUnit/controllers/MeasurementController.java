package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.MeasurementDTO;
import navope.rento.computingUnit.services.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/measurement")
@RequiredArgsConstructor
public class MeasurementController {
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;

    @PostMapping("/new")
    public void addNewMeasurement(@RequestBody MeasurementDTO measurementDTO) {
    }

}
