package navope.rento.sensorsAndController;

import navope.rento.sensorsAndController.dto.SensorDataDTO;
import navope.rento.sensorsAndController.dto.PressureDTO;
import navope.rento.sensorsAndController.dto.TemperatureDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;

public class Main {

    public final static int QUANTITY  = 1000;

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        Random random = new Random();

        String postDataUrl = "http://localhost:8080/sensor_readings/new";

        int i = 0;
        int count = 0;
        while (count++ < QUANTITY) {
            try {
                TemperatureDTO temperatureDTO = new TemperatureDTO(new Date(), STM32F103C8T6.getTemperature());
                PressureDTO pressureDTO = new PressureDTO(new Date(), STM32F103C8T6.getPressure());
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(postDataUrl,
                        new HttpEntity<>(new SensorDataDTO(pressureDTO, temperatureDTO)), String.class);
                System.out.println(i++ + " - " + responseEntity.getStatusCodeValue());
            } catch (HttpClientErrorException e) {
                // Обработка ошибок HTTP-запроса
                System.out.println(i + " - Fail!");
            }
        }
    }

    public static class LM35 {
        public static double getValue() {
            return 5;
        }
    }

    public static class U5244_000005_030PA {
        public static double getValue() {
            return 3.5;
        }
    }

    public static class STM32F103C8T6 {
        public static double adc(double analogValue) {
            // преобразование в цифровое значение
            return analogValue;
        }
        public static double getTemperature() {
            return STM32F103C8T6.adc(LM35.getValue())/0.10;
        }
        public static double getPressure() {
            return STM32F103C8T6.adc(U5244_000005_030PA.getValue())/0.10000;
        }
    }
}

