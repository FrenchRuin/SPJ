package com.example.spj.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ToyProjectServiceTest {

    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    private static final String NAVER_CLIENT_ID = "0QJhbYw2IhHIs78ihpxV";
    private static final String NAVER_SECRET = "LSz5CRvuF4";


    @DisplayName("1. PAPAGO TEST")
    @Test
    void test_1(){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.add("X-Naver-Client-Secret", NAVER_SECRET);

        JSONObject object = new JSONObject();
        object.put("source", "en");
        object.put("target", "ko");
        object.put("text", "I want to talk with you");
        HttpEntity<String> request = new HttpEntity<>(object.toString(),headers);

        ResponseEntity<String> response = restTemplate.postForEntity(PAPAGO_API_URL, request,String.class);
    }


}