package navope.rento.computingUnit.services;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.models.Temperature;
import navope.rento.computingUnit.repositories.TemperatureRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureService {
    private final TemperatureRepository temperatureRepository;

//    public Temperature getLastTemperature() {
//        return temperatureRepository.
//    }
}
