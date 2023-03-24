package com.example.spj.service;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KarloService {


    public static final String API_URL = "https://api.kakaobrain.com/v1/inference/karlo/t2i";


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Prompt {
        private String text;
        private Integer batch_size;

    }

    private static final String REST_API_KEY = "bb67167c460f9277d6a14292f196b274";

    public void callLink() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "KakaoAK "+ REST_API_KEY);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text","Tree");
        jsonObject.put("batch_size","");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("prompt",jsonObject);

        HttpEntity<String> request = new HttpEntity<>(jsonObject1.toString(),httpHeaders);

        String response = restTemplate.postForObject(API_URL,request,String.class);

        log.info("{}",response);

    }

}
