package navope.rento.sensorsAndController;

import navope.rento.sensorsAndController.dto.MeasurementDTO;
import navope.rento.sensorsAndController.dto.PressureDTO;
import navope.rento.sensorsAndController.dto.TemperatureDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class Main {

    public final static int QUANTITY  = 100;
    public final static Double MAX_VALUE = 45.0;
    

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        // MicrocontrollerDTO microcontroller = new MicrocontrollerDTO("SM32");

        Random random = new Random();

        String postMeasurementUrl = "http://localhost:8080/measurement/new";

        //register Microcontroller
//        System.out.println(restTemplate.postForObject(registerSensorUrl,
//                sensor,HttpStatus.class));

        //Adding measurement
        for (int i =0 ; i < QUANTITY; i++) {
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class LM35 {
        public static final double VOLTAGE_MIN = -0.55;
        public static final double VOLTAGE_MAX = 1.5;
        private static final Random random = new Random();
        public static double getAnalogValue() {
            return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * random.nextDouble();
        }
    }

    public static class U5244_000005_030PA {
        private static final double VOLTAGE_MIN = 1.0;
        private static final double VOLTAGE_MAX = 5.0;
        private static final Random random = new Random();

        private static double getAnalogValue() {
            return VOLTAGE_MIN + (VOLTAGE_MAX - VOLTAGE_MIN) * random.nextDouble();
        }

    }


    public static class STM32F103C8T6 {

        public static final double REFERENCE_VOLTAGE= 3.3;
        public static final double ADC_RANGE = 4096;
        public static double convertVoltageToTemperature(double digitalVoltage) {
            return Math.floor(digitalVoltage / 0.01);
        }
        public static double convertVoltageToPressure(double digitalVoltage) {
            final double psiToPascal = 6894.76;
            final double voltageMin = 1.0;
            final double voltageMax = 5.0;
            final double pressureMaxPsi = 30.0;
            final double pressureMinPsi = 0.0;

            double pressurePsi = ((digitalVoltage - voltageMin) * (pressureMaxPsi - pressureMinPsi)) / (voltageMax - voltageMin) +
                    pressureMinPsi;
            return pressurePsi * psiToPascal;
        }

        public static double adc(double analogVoltage) {
            double digitalValue = (analogVoltage / REFERENCE_VOLTAGE) * ADC_RANGE;
            double digitalVoltage = (digitalValue / ADC_RANGE) * REFERENCE_VOLTAGE;
            return Math.floor(digitalVoltage);
        }

        public static double getTemperature() {
            double digitalValue =  adc((LM35.getAnalogValue()));
            return convertVoltageToTemperature(digitalValue);
        }

        public static double getPressure() {
            double digitalValue = adc(U5244_000005_030PA.getAnalogValue());
            return convertVoltageToPressure(digitalValue);
        }
    }
}


