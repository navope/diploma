package navope.rento.sensorsAndController.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TemperatureDTO {
    private Date receivedAt;
    private double value;
}