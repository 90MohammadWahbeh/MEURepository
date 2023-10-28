package meu.edu.jo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu.edu.jo.entities.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}