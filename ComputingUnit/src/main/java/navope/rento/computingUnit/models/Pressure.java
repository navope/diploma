package navope.rento.computingUnit.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Pressure")
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
    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    Airplane airplane;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "received_at")
    private Date receivedAt;
    @Column(name = "value")
    private double value;
}
