package navope.rento.computingUnit.services;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.AlertDTO;
import navope.rento.computingUnit.models.Measurement;
import navope.rento.computingUnit.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    final static double PRESSURE_MIN = 60000;
    final static double PRESSURE_MAX = 115000;
    final static double TEMPERATURE_MIN = 10;
    final static double TEMPERATURE_MAX = 28;
    private final MeasurementRepository measurementRepository;
    public void saveMeasurement(double temperatureValue, double pressureValue) {
        Measurement measurement = Measurement.builder()
                .receivedAt(new Date())
                .pressure(pressureValue)
                .temperature(temperatureValue)
                .build();
        measurementRepository.save(measurement);
    }

    public AlertDTO analysisMeasurement(double temperature, double pressure) {

        boolean pressureAlert = pressure < PRESSURE_MIN || pressure > PRESSURE_MAX;
        boolean temperatureAlert = temperature < TEMPERATURE_MIN || temperature > TEMPERATURE_MAX;

        AlertDTO alert = new AlertDTO();
        alert.setPressure(pressure);
        alert.setTemperature(temperature);
        alert.setPressureAlert(pressureAlert);
        alert.setTemperatureAlert(temperatureAlert);

        if (pressureAlert && temperatureAlert) {
            alert.setMessage("Предупреждение! Превышено максимально допустимые значения давления и температуры!");
        } else if (pressureAlert) {
            alert.setMessage("Предупреждение! Превышено максимально допустимое значение давления!");
        } else if (temperatureAlert) {
            alert.setMessage("Предупреждение! Превышено максимально допустимое значение температуры!");
        } else {
            alert.setMessage("Показания в норме.");
        }
        return alert;
    }
}
