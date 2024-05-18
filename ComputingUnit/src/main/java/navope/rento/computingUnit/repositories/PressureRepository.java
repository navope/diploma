package navope.rento.computingUnit.repositories;

import navope.rento.computingUnit.models.Pressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PressureRepository extends JpaRepository<Pressure, Integer> {
}
