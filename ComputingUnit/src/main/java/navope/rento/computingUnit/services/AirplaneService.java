package navope.rento.computingUnit.services;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.SensorDataDTO;
import navope.rento.computingUnit.models.Airplane;
import navope.rento.computingUnit.models.Pressure;
import navope.rento.computingUnit.models.Temperature;
import navope.rento.computingUnit.repositories.AirplaneRepository;
import navope.rento.computingUnit.repositories.PressureRepository;
import navope.rento.computingUnit.repositories.TemperatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirplaneService {

    private final PressureRepository pressureRepository;
    private final TemperatureRepository temperatureRepository;
    private final AirplaneRepository airplaneRepository;

    public Pressure addNewPressureValue(SensorDataDTO sensorDataDTO, int airplaneId) {
        Optional<Airplane> airplane = airplaneRepository.findById(airplaneId);
        if (airplane.isPresent()) {
//            List<Pressure> pressuresPast = airplane.get().getPressures().add();
            List<Temperature> temperaturesPast = airplane.get().getTemperatures();
        }
        return null;
    }

}