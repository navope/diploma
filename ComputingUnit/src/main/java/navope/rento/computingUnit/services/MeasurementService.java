package navope.rento.computingUnit.services;

import lombok.RequiredArgsConstructor;
import navope.rento.computingUnit.dto.ProcessedMeasurementDTO;
import navope.rento.computingUnit.models.Measurement;
import navope.rento.computingUnit.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    final static double PRESSURE_MIN = 75000;
    final static double PRESSURE_MAX = 85000;
    final static double TEMPERATURE_MIN = 20;
    final static double TEMPERATURE_MAX = 28;
    private final MeasurementRepository measurementRepository;
    public void saveMeasurement(double temperatureValue, double pressureValue) {
        measurementRepository.save(new Measurement(new Date(), pressureValue, temperatureValue));
    }

    public ProcessedMeasurementDTO analysisMeasurement(double temperature, double pressure) {

        boolean pressureAlert = pressure < PRESSURE_MIN || pressure > PRESSURE_MAX;
        boolean temperatureAlert = temperature < TEMPERATURE_MIN || temperature > TEMPERATURE_MAX;

        ProcessedMeasurementDTO processedMeasurement = new ProcessedMeasurementDTO();
        processedMeasurement.setPressure(pressure);
        processedMeasurement.setTemperature(temperature);
        processedMeasurement.setPressureAlert(pressureAlert);
        processedMeasurement.setTemperatureAlert(temperatureAlert);

        if (pressureAlert && temperatureAlert) {
            processedMeasurement.setMessage("Предупреждение! Превышено максимально допустимые значения давления и температуры!");
        } else if (pressureAlert) {
            processedMeasurement.setMessage("Предупреждение! Превышено максимально допустимое значение давления!");
        } else if (temperatureAlert) {
            processedMeasurement.setMessage("Предупреждение! Превышено максимально допустимое значение температуры!");
        } else {
            processedMeasurement.setMessage("Показания в норме.");
        }
        return processedMeasurement;
    }
}
