package com.example.spj.util;

import com.example.spj.config.UserService;
import com.example.spj.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DBInit implements InitializingBean {

    private final UserService userService;


    @Override
    public void afterPropertiesSet() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userService.findUser("toxic023").isEmpty()) {
            User director = userService.saveUser(
                    User.builder()
                            .username("toxic023")
                            .password(passwordEncoder.encode("as2646"))
                            .enabled(true)
                            .build()
            );
            userService.addAuthority(director.getUserId(), "ROLE_ADMIN");
        }
    }
}
