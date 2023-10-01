package com.example.spj.service;

import com.example.spj.dto.UserDto;
import com.example.spj.entity.User;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Not found UserEntity")
        );
    }

    public void saveUser(UserDto userDto) {
        User user = User.userDtoToEntity(userDto);
        userRepository.save(user);
    }

}
