package navope.rento.sensorsAndController;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class STM32F103C8T6 {
    final static double REFERENCE_VOLTAGE = 3.3;
    final static double DIGITAL_VALUE_MIN = 0;
    final static double DIGITAL_VALUE_MAX = 4095;
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        Boolean isStopReading = false;

        String postMeasurementUrl = "http://localhost:8080/measurement/new";
        String getMonitoringStateUrl = "http://localhost:8080/measurement/monitoring_state";

        int i = 0;
        //Adding measurement
        while (Boolean.FALSE.equals(isStopReading)) {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(getMonitoringStateUrl, Boolean.class);
            isStopReading = response.getBody();
            try {
                System.out.println(i + " - " + restTemplate.postForObject(postMeasurementUrl,
                        new HttpEntity<>(new MeasurementDTO(getPressure(), getTemperature())), HttpStatus.class));
                i++;
                Thread.sleep(3000);
            }catch (HttpClientErrorException e){
                System.out.println(i + " - Fail!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

        public static double adc(double analogVoltage) {
            double digitalValue = (analogVoltage / REFERENCE_VOLTAGE) * DIGITAL_VALUE_MAX;
            return Math.floor(digitalValue);
        }

        public static double getTemperature() {
            double digitalValue = adc(TMP36.getAnalogValue());
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
            return (digitalValue * REFERENCE_VOLTAGE / DIGITAL_VALUE_MAX - TMP36.VOLTAGE_OFFSET) * 100;
        }

        public static double convertVoltageToPressure(double digitalValue) {
            final double psiToPascal = 6894.76;
            final double pressureMaxPsi = 30.0;
            final double pressureMax = pressureMaxPsi * psiToPascal;

            double pressure = ((digitalValue * REFERENCE_VOLTAGE - 2702.7) / 10810.8) * pressureMax;
            return pressure;
        }

    public static class TMP36 {
        final static double VOLTAGE_OFFSET = 0.5;
        public static final double VOLTAGE_MIN = 0.1;
        public static final double VOLTAGE_MAX = 1.75;
        public static final double NOISE_LEVEL = 0.1;
        private static final Random random = new Random();

        public static double getAnalogValue() {
            double noise = random.nextDouble() * NOISE_LEVEL;
            return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * 0.35 + noise;
        }
    }

    public static class U5244_000005_030PA {
        private static final double VOLTAGE_MIN = 1.0;
        private static final double VOLTAGE_MAX = 5.0;
        private static final Random random = new Random();

        public static final double NOISE_LEVEL = 0.62;

        public static double getAnalogValue() {
            double noise = random.nextDouble() * NOISE_LEVEL;
            return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * 0.5 - noise;
        }
    }

    public static class VoltageDivider {
        private static final double R1 = 1700; // 1.7 kOhm
        private static final double R2 = 3300; // 3.3 kOhm

        public static double convert(double inputVoltage) {
            double coefficient = (R2 / (R1 + R2)); // 0.66
            return inputVoltage * coefficient; // [1;5] - > [0.66; 3.3]
        }
    }
}


