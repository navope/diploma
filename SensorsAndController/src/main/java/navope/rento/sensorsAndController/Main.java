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
//    public static void main(String[] args) {
//        for (int i = 0; i < 50; i ++) {
//            double a = LM35.getAnalogValue();
//            System.out.println(a * 100);
//            double b = STM32F103C8T6.getTemperature();
//            System.out.println(b);
//        }
//    }

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

    public static class TMP36 {
        public static final double THRESHOLD_VALUE_VOLTAGE_IN_DIGITAL_FORM = 81.92;
        public static final double TEMPERATURE_MIN = -40;
        final static double VOLTAGE_OFFSET = 0.5;
        public static final double VOLTAGE_MIN = 0;
        public static final double VOLTAGE_MAX = 1.75;
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
        final static double REFERENCE_VOLTAGE = 5;
        final static double DIGITAL_VALUE_MIN = 0;
        final static double DIGITAL_VALUE_MAX = 4095 ;
        public static double adc(double analogVoltage) {
            double digitalValue = (analogVoltage / U5244_000005_030PA.VOLTAGE_MAX) * DIGITAL_VALUE_MAX;
            return digitalValue;
        }

        public static double getTemperature() {
            double digitalValue =  adc((TMP36.getAnalogValue()));
            return convertVoltageToTemperature(digitalValue);
        }

        public static double getPressure() {
            double digitalValue = adc(U5244_000005_030PA.getAnalogValue());
            return convertVoltageToPressure(digitalValue);
        }
        public static double convertVoltageToTemperature(double digitalValue) {
            if (digitalValue > TMP36.THRESHOLD_VALUE_VOLTAGE_IN_DIGITAL_FORM) {
                return (digitalValue * REFERENCE_VOLTAGE / STM32F103C8T6.DIGITAL_VALUE_MAX - TMP36.VOLTAGE_OFFSET) /
                        0.01;
            }else {
                return TMP36.TEMPERATURE_MIN;
            }
        }
        public static double convertVoltageToPressure(double digitalValue) {
            final double psiToPascal = 6894.76;
            final double pressureMaxPsi = 30.0;
            final double pressureMinPsi = 0.0;
            final double pressureMax = pressureMaxPsi * psiToPascal;
            final double pressureMix = pressureMinPsi * psiToPascal;

            double pressure = pressureMix + ((digitalValue - DIGITAL_VALUE_MIN) * (pressureMax - pressureMix)) /
                    (DIGITAL_VALUE_MAX - DIGITAL_VALUE_MIN);
            return pressure;
        }
    }
}


