package navope.rento.sensorsAndController;

import navope.rento.sensorsAndController.dto.MeasurementDTO;
import navope.rento.sensorsAndController.dto.PressureDTO;
import navope.rento.sensorsAndController.dto.TemperatureDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class Main {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        Random random = new Random();
        Boolean isStopReading = false;

        String postMeasurementUrl = "http://localhost:8080/measurement/new";
        String getMonitoringStateUrl = "http://localhost:8080/measurement/monitoring_state";


        //Adding measurement
        while (Boolean.FALSE.equals(isStopReading)) {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(getMonitoringStateUrl, Boolean.class);
            isStopReading = response.getBody();
            int i = 0;
            try {
                PressureDTO pressureDTO = PressureDTO.builder()
                        .value(STM32F103C8T6.getPressure())
                        .receivedAt(new Date())
                        .build();
                TemperatureDTO temperatureDTO = TemperatureDTO.builder()
                        .value(STM32F103C8T6.getTemperature())
                        .receivedAt(new Date())
                        .build();
                System.out.println(i + " - " + restTemplate.postForObject(postMeasurementUrl,
                        new HttpEntity<>(new MeasurementDTO(pressureDTO,temperatureDTO)), HttpStatus.class));
                Thread.sleep(3000);
            }catch (HttpClientErrorException e){
                System.out.println(i + " - Fail!");
                i++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class TMP36 {
        public static final double POWER_SUPPLY_VOLTAGE = 2.7;
        public static final double THRESHOLD_VALUE_VOLTAGE_IN_DIGITAL_FORM = 81.92;
        public static final double TEMPERATURE_MIN = -40;
        final static double VOLTAGE_OFFSET = 0.5;
        public static final double VOLTAGE_MIN = 0.1;
        public static final double VOLTAGE_MAX = 1.75;
        public static final double NOISE_LEVEL = 0.2;
        private static final Random random = new Random();

        public static double getAnalogValue() {
            //return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * random.nextDouble();
            double noise = random.nextDouble() * NOISE_LEVEL;
            return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * 0.35 + noise;
        }
    }

    public static class U5244_000005_030PA {
        private static final double VOLTAGE_MIN = 1.0;
        private static final double VOLTAGE_MAX = 5.0;
        private static final Random random = new Random();

        public static final double NOISE_LEVEL = 0.7;

        public static double getAnalogValue() {
            //return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * random.nextDouble();
            double noise = random.nextDouble() * NOISE_LEVEL;
            return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * 0.3 + noise;
        }
    }

    public static class VoltageDivider {
        private static final double R1 = 1700; // 1.7 kOhm
        private static final double R2 = 3300; // 3.3 kOhm

        public static double convert(double inputVoltage) {
//            double coefficient = (double) Math.round((R2 / (R1 + R2) * 100)) / 100;
            double coefficient = (R2 / (R1 + R2));
            return inputVoltage * coefficient;
        }
    }

    public static class STM32F103C8T6 {
        final static double REFERENCE_VOLTAGE = 3.3;
        final static double DIGITAL_VALUE_MIN = 0;
        final static double DIGITAL_VALUE_MAX = 4095;

        public static double adc(double analogVoltage) {
            double digitalValue = (analogVoltage / REFERENCE_VOLTAGE) * DIGITAL_VALUE_MAX;
            return Math.floor(digitalValue);
        }

        public static double getTemperature() {
            double digitalValue = adc(TMP36.getAnalogValue());
            //return convertVoltageToTemperature(digitalValue);
            return Math.round(convertVoltageToTemperature(digitalValue));
        }

        public static double getPressure() {
            double analogValue = U5244_000005_030PA.getAnalogValue();
            double convertedValue = VoltageDivider.convert(analogValue);
            double digitalValue = adc(convertedValue);
            //return convertVoltageToPressure(digitalValue);
            return Math.round(convertVoltageToPressure(digitalValue));
        }

        public static double convertVoltageToTemperature(double digitalValue) {
            return (digitalValue * REFERENCE_VOLTAGE / DIGITAL_VALUE_MAX - TMP36.VOLTAGE_OFFSET) / 0.01;
        }

        public static double convertVoltageToPressure(double digitalValue) {
            final double psiToPascal = 6894.76;
            final double pressureMaxPsi = 30.0;
            final double pressureMinPsi = 0.0;
            final double pressureMax = pressureMaxPsi * psiToPascal;
            final double pressureMin = pressureMinPsi * psiToPascal;

            double pressure = pressureMin + ((digitalValue - DIGITAL_VALUE_MIN) * (pressureMax - pressureMin)) /
                    (DIGITAL_VALUE_MAX - DIGITAL_VALUE_MIN);
            return pressure;
        }
    }
}


