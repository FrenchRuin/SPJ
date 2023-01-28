package com.example.spj.config;

import com.example.spj.entity.user.User;
import com.example.spj.entity.user.UserAuthority;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 ID >>>>> {} ", username);
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException(username + "가 없습니다.")
        );
    }

    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void addAuthority(Long userId, String authority) {
        userRepository.findById(userId).ifPresent(user -> {
                    UserAuthority role = new UserAuthority(userId, authority);
                    if (user.getAuthorities() == null) {
                        HashSet<UserAuthority> authorities = new HashSet<>();
                        authorities.add(role);
                        user.setAuthorities(authorities);
                        saveUser(user);
                    } else if (!user.getAuthorities().contains(role)) {
                        HashSet<UserAuthority> authorities = new HashSet<>(user.getAuthorities());
                        authorities.add(role);
                        user.setAuthorities(authorities);
                        saveUser(user);
                    }
                }
        );
    }

    public void signUp(User user) {
        log.info("회원가입 요청 >>> {}", user.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User spjUser = saveUser(User.builder()
                .username(user.getUsername())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .password(passwordEncoder.encode(user.getPassword()))
                .enabled(true)
                .build());

        addAuthority(spjUser.getUserId(),"ROLE_USER");
    }
}
