package com.autobots.ms.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/vendas")
public class VendasControle {

	@Autowired
	private RestTemplate restTemplate;
	private final String baseUrl = "http://localhost:8080/relatorios/empresas";

	@GetMapping("/empresas/{id}")
	public ResponseEntity<Object> getVendasPeriodo(
			@PathVariable Long id,
			@RequestParam String inicio,
			@RequestParam String fim,
			@RequestHeader("Authorization") String authHeader) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		String url = String.format("%s/%d/vendas-periodo?inicio=%s&fim=%s", baseUrl, id, inicio, fim);
		return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
	}
}
