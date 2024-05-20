package navope.rento.computingUnit.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "microcontroller")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Microcontroller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registered_at")
    private Date registeredAt;
}