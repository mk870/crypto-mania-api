package cryptoMania.WebConfig;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cryptoMania.JwtFilter.JwtFilter;
import cryptoMania.Services.UserDetailsServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
  @Autowired
  UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired
  private JwtFilter jwtFilter;
  final private String[] PermitedUrls = {"/api/user","/api/login","/api/home","/api/news","/api/signup","/api/verifyRegistration/**"};

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(11);
  }
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
  }
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception{
    return super.authenticationManagerBean();
  }
  @Override
  
  protected void configure(HttpSecurity http) throws Exception{
    
    http
        .cors()
        .and()
        .csrf()
        .disable();
    http
        .authorizeRequests()
        .antMatchers(PermitedUrls).permitAll()
        
        .anyRequest().authenticated()
        .and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    
  }
}
