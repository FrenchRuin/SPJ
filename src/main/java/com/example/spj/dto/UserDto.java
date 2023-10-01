package com.example.spj.dto;

import com.example.spj.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String password;
    private String username;
    private boolean enabled;
    private List<BoardDto> boards;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    /*
    * UserEntity to UserDto Converter
    * */
    public static UserDto userEntityToDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .password(user.getPassword())
                .updatedTime(user.getUpdatedTime())
                .createdTime(user.getCreatedTime())
                .userId(user.getUserId())
                .build();
    }
}
