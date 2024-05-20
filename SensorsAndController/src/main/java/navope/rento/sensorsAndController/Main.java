package navope.rento.sensorsAndController;

import navope.rento.sensorsAndController.dto.MeasurementDTO;
import navope.rento.sensorsAndController.dto.PressureDTO;
import navope.rento.sensorsAndController.dto.TemperatureDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class Main {

    public final static int QUANTITY  = 1000;
    public final static Double MAX_VALUE = 45.0;

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        // MicrocontrollerDTO microcontroller = new MicrocontrollerDTO("SM32");

        Random random = new Random();

        String postMeasurementUrl = "http://localhost:8080/measurement/add";

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
            }catch (HttpClientErrorException e){
                System.out.println(i + " - Fail!");
            }
        }
    }

    public static class LM35 {
        public static double getValue() {
            return 5; // Здесь должно быть реальное значение от датчика LM35
        }
    }

    public static class U5244_000005_030PA {
        public static double getValue() {
            return 3.5; // Здесь должно быть реальное значение от датчика U5244-000005-030PA
        }
    }

    public static class STM32F103C8T6 {
        public static double adc(double analogValue) {
            // Преобразование в цифровое значение
            return analogValue;
        }

        public static double getTemperature() {
            return adc(LM35.getValue()) / 0.10; // Примерная формула преобразования для LM35
        }

        public static double getPressure() {
            return adc(U5244_000005_030PA.getValue()) / 0.10000; // Примерная формула преобразования для U5244-000005-030PA
        }
    }
}


