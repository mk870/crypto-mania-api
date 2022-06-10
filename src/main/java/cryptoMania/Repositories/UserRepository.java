package cryptoMania.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cryptoMania.Entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
  User findByEmail(String email);
}
