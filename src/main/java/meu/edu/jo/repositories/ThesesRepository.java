package meu.edu.jo.repositories;

import meu.edu.jo.entities.Theses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesesRepository extends JpaRepository<Theses, Long> {
    // You can add custom query methods if needed
}
