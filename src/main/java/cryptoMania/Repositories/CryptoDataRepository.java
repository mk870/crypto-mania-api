package cryptoMania.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cryptoMania.Entities.User;
import cryptoMania.Entities.CryptoData;

public interface CryptoDataRepository extends JpaRepository<CryptoData,Long> {

  List<CryptoData> findByUser(User user);
 
}
