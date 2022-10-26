package com.example.sotisproject.security.authority;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private JWToken jWToken;
	private UserDetailsService userDetailsService;
	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(JWToken tokenHelper, UserDetailsService userDetailsService) {
		this.jWToken = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;

		// 1. Preuzimanje JWT tokena iz zahteva
		String authToken = jWToken.getToken(request);

		try {

			if (authToken != null) {

				// 2. Citanje korisnickog imena iz tokena
				username = jWToken.getUsernameFromToken(authToken);

				if (username != null) {

					// 3. Preuzimanje korisnika na osnovu username-a
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					// 4. Provera da li je prosledjeni token validan
					if (jWToken.validateToken(authToken, userDetails)) {

						// 5. Kreiraj autentifikaciju
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}

		} catch (ExpiredJwtException ex) {
			LOGGER.debug("Token expired!");
		}

		// prosledi request dalje u sledeci filter
		chain.doFilter(request, response);
	}
}
