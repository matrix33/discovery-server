package com.debi.eurekademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
	
	@Autowired
	EurekaClient eurekaClient;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
	@RequestMapping("/")
	public String callService() {
		RestTemplate template = restTemplateBuilder.build();
		InstanceInfo client = eurekaClient.getNextServerFromEureka("service", false);
		String baseURL = client.getHomePageUrl();
		ResponseEntity<String> response = template.exchange(baseURL, HttpMethod.GET, null, String.class);
		return response.getBody();
	}

}
