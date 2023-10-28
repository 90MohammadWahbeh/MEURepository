package meu.edu.jo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu.edu.jo.entities.ActivitiesConferences;

@Repository
public interface ActivitiesConferencesRepository extends JpaRepository<ActivitiesConferences, Long> {
    // Custom repository methods (if needed)
}
