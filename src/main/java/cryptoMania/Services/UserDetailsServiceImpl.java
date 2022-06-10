package cryptoMania.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cryptoMania.Entities.User;
import cryptoMania.Repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if(user == null){
      throw new UsernameNotFoundException("User not found");
    }
   return new org.springframework.security.core.userdetails.User(
    user.getEmail(),
    user.getPassword(),
    user.isEnabled(),
    true,
    true,
    true,
    getAuthorities(List.of(user.getRole()))
   );
  }
  private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles){
    List<GrantedAuthority> authorities = new ArrayList<>();
    for(String role: roles){
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
}
