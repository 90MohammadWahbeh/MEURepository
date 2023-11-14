package meu.edu.jo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu.edu.jo.entities.Activities;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long> {
}
