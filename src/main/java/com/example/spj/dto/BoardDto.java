package com.example.spj.dto;

import com.example.spj.entity.Board;
import com.example.spj.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long board_id;
    private String title;
    private String contents;
    private UserDto user;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    // board Dto to board Entity
    public static BoardDto boardEntityToDto(Board board, User user){


        return BoardDto.builder()
                .board_id(board.getBoard_id())
                .title(board.getTitle())
                .contents(board.getContents())
                .user(UserDto.userEntityToDto(user))
                .createdTime(board.getCreatedTime())
                .updatedTime(board.getUpdatedTime())
                .build();
    }
}
