package cryptoMania.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cryptoMania.DataTransferObjects.CryptoDataDTO;
import cryptoMania.Entities.User;
import cryptoMania.Services.CryptoDataService;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class UserAccountController {
  @Autowired
  CryptoDataService cryptoDataService;

  @GetMapping("/user")
  public String hello(@AuthenticationPrincipal User user){
    
    return "hie there " + user.getFirstName();
  }

  @PostMapping("/save")
  public ResponseEntity<String> saveCrypto (@RequestBody CryptoDataDTO cryptoDataDTO,Authentication user){
    if (user == null) {
      return ResponseEntity.ok("no user");
    }
    
    return ResponseEntity.ok(cryptoDataService.saveCrypto(cryptoDataDTO,user));
  }

  @GetMapping("/cryptos")
  public ResponseEntity<?> getCryptos(Authentication user){
    return ResponseEntity.ok(cryptoDataService.getCryptos(user));
  }

  @DeleteMapping("/crypto/{id}")
  public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id,@AuthenticationPrincipal User user){
    
    return ResponseEntity.ok(cryptoDataService.deleteCrypto(id));
  }
}
