package com.example.spj.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToyProjectServiceTest {

    @Autowired
    private ToyProjectService toyProjectService;

    @DisplayName("1. API Test")
    @Test
    public void test_1(){
        toyProjectService.getKarloImage();
    }



}