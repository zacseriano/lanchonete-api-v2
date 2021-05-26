package com.zacseriano.lanchoneteapi.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.gestor.Gestor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${lanchoneteapi.jwt.expiration}")
	private String expiration;
	
	@Value("${lanchoneteapi.jwt.secret}")
	private String secret;

	public String gerarTokenGestor(Authentication authentication) {
			
		Gestor logado = (Gestor) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API Rest para Lanchonete")
				.setSubject(logado.getEmail().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public String gerarTokenCliente(Authentication authentication) {
		
		Cliente logado = (Cliente) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API Rest para Lanchonete")
				.setSubject(logado.getEmail().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

}