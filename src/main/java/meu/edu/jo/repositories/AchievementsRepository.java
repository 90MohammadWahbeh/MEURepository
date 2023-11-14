package meu.edu.jo.repositories;

import meu.edu.jo.entities.Achievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementsRepository extends JpaRepository<Achievements, Long> {
    // You can add custom query methods if needed
}
