package navope.rento.sensorsAndController.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SensorsDataDTO {
    PressureDTO pressureDTO;
    TemperatureDTO temperatureDTO;
}
