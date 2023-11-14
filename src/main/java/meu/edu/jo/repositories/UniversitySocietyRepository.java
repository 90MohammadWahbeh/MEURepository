package meu.edu.jo.repositories;

import meu.edu.jo.entities.UniversitySociety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversitySocietyRepository extends JpaRepository<UniversitySociety, Long> {
    // You can add custom query methods if needed
}
