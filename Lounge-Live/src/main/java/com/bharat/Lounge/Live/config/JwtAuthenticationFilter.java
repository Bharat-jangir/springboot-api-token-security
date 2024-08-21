package com.bharat.Lounge.Live.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bharat.Lounge.Live.services.JWTService;
import com.bharat.Lounge.Live.services.UserService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JWTService jwtService;

    private final UserService userService;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
      
                final String authHeader= request.getHeader("Authorization");
                final String jwt;
                final String userEmail;

                if(StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer")){
                    filterChain.doFilter(request, response);
                    return;
                }
                jwt=authHeader.substring(7);

                System.out.println(jwt);
                userEmail=jwtService.extractUserName(jwt);
                System.out.println("email:"+userEmail);

                if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
                    System.out.println("manish jangir calling");
                    UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);

                    if(jwtService.isTokenValid(jwt, userDetails)){
                        System.out.println("token is valid");
                        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();

                        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        securityContext.setAuthentication(token);
                        SecurityContextHolder.setContext(securityContext);
                    }
                }
                filterChain.doFilter(request, response);
        
    }

}
