package navope.rento.sensorsAndController.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MeasurementDTO {
    private PressureDTO pressureDTO;
    private TemperatureDTO temperatureDTO;
}