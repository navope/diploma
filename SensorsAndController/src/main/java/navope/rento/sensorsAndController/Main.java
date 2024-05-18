package navope.rento.sensorsAndController;

import com.fasterxml.jackson.databind.ObjectMapper;
import navope.rento.sensorsAndController.dto.SensorsDataDTO;
import navope.rento.sensorsAndController.dto.PressureDTO;
import navope.rento.sensorsAndController.dto.TemperatureDTO;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.util.Date;

//public class Main {
//
//    public final static int QUANTITY  = 1000;
//
//    public static void main(String[] args) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        Random random = new Random();
//
//        String postDataUrl = "http://localhost:8080/sensor_readings/new";
//
//        int i = 0;
//        int count = 0;
//        while (count++ < QUANTITY) {
//            try {
//                TemperatureDTO temperatureDTO = new TemperatureDTO(new Date(), STM32F103C8T6.getTemperature());
//                PressureDTO pressureDTO = new PressureDTO(new Date(), STM32F103C8T6.getPressure());
//                ResponseEntity<String> responseEntity = restTemplate.postForEntity(postDataUrl,
//                        new HttpEntity<>(new SensorDataDTO(pressureDTO, temperatureDTO)), String.class);
//                System.out.println(i++ + " - " + responseEntity.getStatusCodeValue());
//            } catch (HttpClientErrorException e) {
//                // Обработка ошибок HTTP-запроса
//                System.out.println(i + " - Fail!");
//            }
//        }
//    }
//
//    public static class LM35 {
//        public static double getValue() {
//            return 5;
//        }
//    }
//
//    public static class U5244_000005_030PA {
//        public static double getValue() {
//            return 3.5;
//        }
//    }
//
//    public static class STM32F103C8T6 {
//        public static double adc(double analogValue) {
//            // преобразование в цифровое значение
//            return analogValue;
//        }
//        public static double getTemperature() {
//            return STM32F103C8T6.adc(LM35.getValue())/0.10;
//        }
//        public static double getPressure() {
//            return STM32F103C8T6.adc(U5244_000005_030PA.getValue())/0.10000;
//        }
//    }
//}

import java.net.URI;

public class Main {

    public static final int QUANTITY = 5;
    private static WebSocketClient webSocketClient;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        try {
            // Подключение к WebSocket серверу
            connectWebSocket();

            int count = 0;
            while (count++ < QUANTITY) {
                try {
                    TemperatureDTO temperatureDTO = new TemperatureDTO(new Date(), STM32F103C8T6.getTemperature());
                    PressureDTO pressureDTO = new PressureDTO(new Date(), STM32F103C8T6.getPressure());
                    SensorsDataDTO sensorsDataDTO = new SensorsDataDTO(pressureDTO, temperatureDTO);

                    // Преобразование данных в JSON
                    String json = objectMapper.writeValueAsString(sensorsDataDTO);

                    // Отправка данных через WebSocket
                    webSocketClient.send(json);

                    System.out.println(count + " - Data sent");
                } catch (Exception e) {
                    // Обработка ошибок при отправке данных
                    System.out.println(count + " - Fail!");
                    e.printStackTrace();
                }
                // Имитация задержки
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Закрытие соединения
            if (webSocketClient != null) {
                webSocketClient.close();
            }
        }
    }

    private static void connectWebSocket() throws Exception {
//        URI uri = new URI("ws://localhost:8080/interface");
        URI uri = new URI("ws://localhost:8080/sensor_readings/new");

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("WebSocket opened");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received message: " + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket closed with exit code " + code + " additional info: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket error occurred: " + ex.getMessage());
            }
        };

        webSocketClient.connectBlocking();
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


