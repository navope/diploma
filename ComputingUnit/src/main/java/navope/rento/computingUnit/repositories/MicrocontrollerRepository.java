package navope.rento.computingUnit.repositories;

import navope.rento.computingUnit.models.Microcontroller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicrocontrollerRepository extends JpaRepository<Microcontroller, Integer> {
}