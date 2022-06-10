package cryptoMania.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cryptoMania.Entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long>{
  VerificationToken findByToken(String token);
}
