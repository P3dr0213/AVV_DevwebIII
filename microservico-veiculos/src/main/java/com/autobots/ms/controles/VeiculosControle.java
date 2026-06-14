package com.autobots.ms.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/veiculos")
public class VeiculosControle {

	@Autowired
	private RestTemplate restTemplate;
	private final String baseUrl = "http://localhost:8080/relatorios/empresas";

	@GetMapping("/empresas/{id}")
	public ResponseEntity<Object> getVeiculos(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		String url = baseUrl + "/" + id + "/veiculos";
		return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
	}
}
