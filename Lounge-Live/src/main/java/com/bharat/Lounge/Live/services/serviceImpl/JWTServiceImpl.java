package com.bharat.Lounge.Live.services.serviceImpl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bharat.Lounge.Live.services.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService{


    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
        .signWith(getSigninKey(),SignatureAlgorithm.HS256)
        .compact();
    }

    @Override
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token,Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigninKey(){
        byte[] key=Decoders.BASE64.decode("1234567m3456789b345678f2345678k45678k0987654j");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public String generateRefreshToken(Map<String,Object> extraClaims,UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+404800000))
        .signWith(getSigninKey(),SignatureAlgorithm.HS256)
        .compact();
    }

 


}
