package navope.rento.computingUnit.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "measurement")
@NoArgsConstructor
@Getter
@Setter
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "received_at")
    private Date receivedAt;
    @Column(name = "pressure")
    private double pressure;
    @Column(name = "temperature")
    private double temperature;

    public Measurement(Date receivedAt, double pressure, double temperature) {
        this.receivedAt = receivedAt;
        this.pressure = pressure;
        this.temperature = temperature;
    }
}
