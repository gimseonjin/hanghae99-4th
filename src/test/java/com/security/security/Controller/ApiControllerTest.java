package com.security.security.Controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest {

    @LocalServerPort
    int port;

    public String getGreetingUrl(){
        return "http://localhost:" + port + "/api/greeting";
    }
    RestTemplate restTemplate = new RestTemplate();

    @DisplayName("1. 인증 실패")
    @Test
    public void test1(){

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                ()-> restTemplate.getForObject(getGreetingUrl(), String.class)
        );

        assertEquals(401, exception.getRawStatusCode());
    }

    @DisplayName("2. 인증 성공")
    @Test
    public void test2(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(
                "carry:1234".getBytes(StandardCharsets.UTF_8)
        ));

        HttpEntity entity = new HttpEntity(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getGreetingUrl(), HttpMethod.GET, entity, String.class);

        assertEquals("Hello!", response.getBody());
    }

    @DisplayName("3. 인증 성공 with testRestTemplate")
    @Test
    public void test3(){
        TestRestTemplate testRestTemplate = new TestRestTemplate("carry","1234");

        ResponseEntity<String> response = testRestTemplate.postForEntity(getGreetingUrl(), "kimseonjin", String.class);

        assertEquals("Hello kimseonjin", response.getBody());
    }
}