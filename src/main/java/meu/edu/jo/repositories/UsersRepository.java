package meu.edu.jo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;



import meu.edu.jo.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	   public meu.edu.jo.entities.Users findByusername(String userName);
}
