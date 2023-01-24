package com.example.spj.service;

import com.example.spj.entity.user.Authority;
import com.example.spj.entity.user.User;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signUpProcess(User user) {
      log.info("회원가입 요청 >>> {}", user.getUsername());
        Authority newRole = new Authority(1L, "ROLE_USER");
        HashSet<Authority> authorities = new HashSet<>();
        authorities.add(newRole);
        User spjUser = User.builder()
                .username(user.getUsername())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .password(passwordEncoder.encode(user.getPassword()))
                .authorities(authorities)
                .build();

        userRepository.save(spjUser);
    }
}
