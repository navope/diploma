package navope.rento.computingUnit.services;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.models.Pressure;
import navope.rento.computingUnit.models.Temperature;
import navope.rento.computingUnit.repositories.PressureRepository;
import navope.rento.computingUnit.repositories.TemperatureRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final PressureRepository pressureRepository;
    private final TemperatureRepository temperatureRepository;

    public void saveTemperature(Temperature temperature) {
        temperatureRepository.save(temperature);
    }

    public void savePressure(Pressure pressure) {
        pressureRepository.save(pressure);
    }
}
