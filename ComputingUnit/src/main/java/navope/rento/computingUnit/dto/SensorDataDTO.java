package navope.rento.computingUnit.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SensorDataDTO {
    PressureDTO pressureDTO;
    TemperatureDTO temperatureDTO;
}
