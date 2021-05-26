package com.zacseriano.lanchoneteapi.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	/**
	 * Campo do segredo que estará na última parte do JWT
	 */	
	@Value("${lanchoneteapi.jwt.secret}")
    private String SECRET_KEY;
	
	@Value("${lanchoneteapi.jwt.expiration}")
	private String expiration;
    
    /**
	 * Método que extrai o username e insere no claim
	 */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
   	 * Método que extrai a data de expiração e insere no claim
   	 */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
   	 * Método que extrai as claims anteriores e concatena
   	 */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
   	 * Método que faz o parse final das claims para o token de JWT
   	 */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    
    /**
   	 * Método que checa a data de expiração do token
   	 */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
   	 * Método que gera o token recebendo os dados da classe UserDetails, implementados pelas
   	 * classes Cliente ou Gestor
   	 */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    
    /**
   	 * Método que usa o builder() de JWT e constrói o JWT com o header, definindo as claims, os subjects, expiração
   	 * e fechando com a assinatura
   	 */
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
        		.setIssuer("API Rest para Lanchonete")
        		.setClaims(claims)
        		.setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    /**
   	 * Método que valida o token recebendo e checando os dados recebidos da classe UserDetails 
   	 * implementado pelas classes de Cliente e Gestor
   	 */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
