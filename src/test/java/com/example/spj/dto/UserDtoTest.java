package com.example.spj.dto;

import com.example.spj.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class UserDtoTest {

    @DisplayName("1. 엔티티 변환 테스트")
    @Test
    void test_1(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .enabled(true)
                .password(passwordEncoder.encode("qwer"))
                .username("test")
                .userId(1L)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        UserDto userDto = UserDto.userEntityToDto(user);

        log.info(">>>>>>> userDto ==  {}" , userDto.toString());

        User user1 = User.userDtoToEntity(userDto);

        log.info(">>>>>>> userEntity ==  {}" , user1.toString());
    }

}