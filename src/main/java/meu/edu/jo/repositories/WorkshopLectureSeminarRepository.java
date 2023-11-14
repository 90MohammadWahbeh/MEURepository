
package meu.edu.jo.repositories;

import meu.edu.jo.entities.WorkshopLectureSeminar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshopLectureSeminarRepository extends JpaRepository<WorkshopLectureSeminar, Long> {
    // You can add custom query methods if needed
}
