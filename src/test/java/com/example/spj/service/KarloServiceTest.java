package com.example.spj.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@SpringBootTest
@Slf4j
class KarloServiceTest {

    public static final String API_URL = "'https://api.kakaobrain.com/v1/inference/karlo/t2i";


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class KarloResponse {

        private Prompt prompt;

        private String id;
        private String model_version;
        private ArrayList<Image> images;


        @Getter
        @Setter
        public class Image{
            private String id;
            private String image;
            private Boolean nsfw;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Prompt {
        private String text;
        private Integer batch_size;
    }

    private static final String REST_API_KEY = "bb67167c460f9277d6a14292f196b274";

    @DisplayName("1. 호출 테스트 ")
    @Test
    void test_1(){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", REST_API_KEY);


       KarloResponse body = new KarloResponse();
       body.getPrompt().setText("Tree");
       body.getPrompt().setBatch_size(1);

       HttpEntity<KarloResponse> request = new HttpEntity<>(body, httpHeaders);

       KarloResponse response =  restTemplate.postForObject(API_URL,request,KarloResponse.class);


        log.info("{}" , response);


    }

}