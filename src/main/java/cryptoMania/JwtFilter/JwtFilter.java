package cryptoMania.JwtFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cryptoMania.Services.UserDetailsServiceImpl;

@Component
public class JwtFilter extends OncePerRequestFilter{
  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired
  private JwtUtil jwtUtil;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
     //Get authorization header and validate
     String token = null;
     String email = null;
    final String header = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
    if(header != null && header.startsWith("Bearer ")){
       token = header.split(" ")[1].trim();
       email = jwtUtil.extractUsername(token);
    }
    //Get jwt token and validate
    
    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
      if(jwtUtil.validateToken(token,userDetails)){
       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
       usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
       SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
     }
    }
    filterChain.doFilter(request, response);
  }
}
