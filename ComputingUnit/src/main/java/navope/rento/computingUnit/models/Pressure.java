package navope.rento.computingUnit.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pressure")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pressure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "received_at")
    private Date receivedAt;
    @Column(name = "value")
    private double value;
}
