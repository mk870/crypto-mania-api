package cryptoMania.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import cryptoMania.DataTransferObjects.CryptoDataDTO;
import cryptoMania.Entities.User;
import cryptoMania.Entities.CryptoData;
import cryptoMania.Repositories.CryptoDataRepository;
import cryptoMania.Repositories.UserRepository;

@Service
public class CryptoDataService {
  @Autowired
  CryptoDataRepository cryptoDataRepository;
  @Autowired
  UserRepository userRepository;

  public String saveCrypto(CryptoDataDTO cryptoDataDTO,Authentication user){
    CryptoData cryptoDetails = new CryptoData();
    cryptoDetails.setName(cryptoDataDTO.getName());
    cryptoDetails.setMktCap(cryptoDataDTO.getMktCap());
    cryptoDetails.setAllTimeHighPrice(cryptoDataDTO.getAllTimeHighPrice());
    cryptoDetails.setCoinsInCirculation(cryptoDataDTO.getCoinsInCirculation());
    cryptoDetails.setRank(cryptoDataDTO.getRank());
    cryptoDetails.setUser(userRepository.findByEmail(user.getName()));
    CryptoData savedCrypto = cryptoDataRepository.save(cryptoDetails);
    return savedCrypto.getName()+" added to your watchlist successfully";
  }

  public List<CryptoData> getCryptos(Authentication principal){
    User user = userRepository.findByEmail(principal.getName());
    List<CryptoData> cryptos = cryptoDataRepository.findByUser(user);
    if (cryptos == null) {
      return null;
    }
    return cryptos;
  }

  public String deleteCrypto(Long id) {
    cryptoDataRepository.deleteById(id);
    return "Crypto has been successfully deleted";
  }
}
