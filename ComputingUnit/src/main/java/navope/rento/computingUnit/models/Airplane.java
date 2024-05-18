package navope.rento.computingUnit.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Airplane")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "airplane")
    private List<Pressure> pressures;
    @OneToMany(mappedBy = "airplane")
    private List<Temperature> Temperatures;
}
