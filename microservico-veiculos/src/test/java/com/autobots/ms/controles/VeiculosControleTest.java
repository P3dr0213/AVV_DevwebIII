package com.autobots.ms.controles;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest
@AutoConfigureMockMvc
public class VeiculosControleTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestTemplate restTemplate;

	private String validJwtToken;

	@BeforeEach
	void setUp() {
		String secret = "autobots-automanager-chave-secreta-jwt-2024";
		String token = Jwts.builder()
				.setSubject("admin")
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		validJwtToken = "Bearer " + token;

		ResponseEntity<Object> response = new ResponseEntity<>(Collections.singletonMap("status", "ok"), HttpStatus.OK);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class)))
				.thenReturn(response);
	}

	@Test
	public void testGetVeiculos() throws Exception {
		mockMvc.perform(get("/veiculos/empresas/1")
				.header("Authorization", validJwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testUnauthorizedWithoutToken() throws Exception {
		mockMvc.perform(get("/veiculos/empresas/1"))
				.andExpect(status().isForbidden());
	}
}
