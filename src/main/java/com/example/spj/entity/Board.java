package com.example.spj.entity;


import com.example.spj.dto.BoardDto;
import com.example.spj.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "spj_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    private String title;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(updatable = false)
    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    // board Entity to Dto
    public static Board boardDtoToEntity(BoardDto boardDto){

        User user = User.userDtoToEntity(boardDto.getUser());
        return Board.builder()
                .board_id(boardDto.getBoard_id())
                .title(boardDto.getTitle())
                .user(user)
                .contents(boardDto.getContents())
                .createdTime(boardDto.getCreatedTime())
                .updatedTime(boardDto.getUpdatedTime())
                .build();
    }
}
