package cryptoMania.Controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cryptoMania.DataTransferObjects.LoginDTO;
import cryptoMania.DataTransferObjects.SignUpDTO;
import cryptoMania.DataTransferObjects.UserDTO;
import cryptoMania.Services.UserServiceImpl;


@RestController

@RequestMapping("/api")
@CrossOrigin
public class SignupController {
  @Autowired
  private UserServiceImpl userServiceImpl;

  @GetMapping("/home")
  public String welcome(){
    return "welcome to cryptoMania database";
  }

  @PostMapping("/signup")
  public ResponseEntity<?> Signup(@RequestBody UserDTO user) throws MessagingException{
    String response = userServiceImpl.saveUser(user);
    return ResponseEntity.ok(response);
  }
  
  @GetMapping("/verifyRegistration")
  public SignUpDTO verifyRegistration(@RequestParam("token") String token){
    return userServiceImpl.validateToken(token);
    
  }
  @PostMapping("/login")
  public ResponseEntity<?> userLogin(@RequestBody LoginDTO loginDTO){
    
    return ResponseEntity.ok(userServiceImpl.userLogin(loginDTO));
  }
}
