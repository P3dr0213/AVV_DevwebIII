package com.autobots.ms.filtros;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AutorizadorFiltro extends OncePerRequestFilter {

	private final String secret = "autobots-automanager-chave-secreta-jwt-2024";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				Claims claims = Jwts.parser()
						.setSigningKey(secret.getBytes())
						.parseClaimsJws(token)
						.getBody();
				
				String username = claims.getSubject();
				if (username != null && claims.getExpiration().after(new java.util.Date())) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							username, 
							null, 
							Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
					);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (Exception e) {
				// Invalid token
			}
		}
		
		filterChain.doFilter(request, response);
	}
}
