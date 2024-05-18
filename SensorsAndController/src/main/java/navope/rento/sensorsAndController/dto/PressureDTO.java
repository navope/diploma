package navope.rento.sensorsAndController.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PressureDTO {
    private Date receivedAt;
    private double value;
}