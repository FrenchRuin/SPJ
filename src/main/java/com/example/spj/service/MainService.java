package com.example.spj.service;

import com.example.spj.entity.user.UserAuthority;
import com.example.spj.entity.user.User;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    public void signUpProcess(User user) {

      log.info("회원가입 요청 >>> {}", user.getUsername());
        User spjUser = User.builder()
                .username(user.getUsername())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .password(passwordEncoder().encode(user.getPassword()))
                .enabled(true)
                .build();
         User savedUser =  userRepository.save(spjUser);

        UserAuthority newRole = new UserAuthority(savedUser.getUserId(), "ROLE_USER");
        HashSet<UserAuthority> authorities = new HashSet<>();
        authorities.add(newRole);
        savedUser.setAuthorities(authorities);

        userRepository.save(savedUser);
    }
}
