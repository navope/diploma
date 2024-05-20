package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.MicrocontrollerDTO;
import navope.rento.computingUnit.models.Microcontroller;
import navope.rento.computingUnit.services.MicrocontrollerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class MicrocontrollerController {

    private final ModelMapper modelMapper;
    private final MicrocontrollerService microcontrollerService;

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid MicrocontrollerDTO microcontrollerDTO,
                                                   BindingResult bindingResult) {
        Microcontroller microcontroller = convertToMicrocontroller(microcontrollerDTO);

        microcontrollerService.register(microcontroller);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Microcontroller convertToMicrocontroller(MicrocontrollerDTO microcontrollerDTO) {
        return modelMapper.map(microcontrollerDTO, Microcontroller.class);
    }
}