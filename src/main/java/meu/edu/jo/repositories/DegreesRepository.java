package meu.edu.jo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu.edu.jo.entities.Degrees;

@Repository
public interface DegreesRepository extends JpaRepository<Degrees, Long> {
    // Custom repository methods (if needed)
}
