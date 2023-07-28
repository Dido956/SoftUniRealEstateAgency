package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;

@Repository
public interface ApartmentRepository  extends JpaRepository<Apartment, Long> {

    boolean existsByTownTownNameAndArea(String town, Double area);
}
