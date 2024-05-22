package navope.rento.computingUnit.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProcessedMeasurementDTO {
    private String message;
    private boolean pressureAlert;
    private boolean temperatureAlert;
    private double pressure;
    private double temperature;
}
