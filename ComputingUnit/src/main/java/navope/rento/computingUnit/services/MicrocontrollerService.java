package navope.rento.computingUnit.services;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.models.Microcontroller;
import navope.rento.computingUnit.repositories.MicrocontrollerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MicrocontrollerService {

    private MicrocontrollerRepository microcontrollerRepository;
    public void register(Microcontroller microcontroller) {
        microcontroller.setRegisteredAt(new Date());
        microcontrollerRepository.save(microcontroller);
    }
}
