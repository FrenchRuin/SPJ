package com.example.spj.service;

import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToyProjectService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Board> findAllBoard(){
        return boardRepository.findAll();
    }

    public void saveBoard(Board board, User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(x->{
            board.setCreated(LocalDateTime.now());
            board.setUser(x);
        });
        boardRepository.save(board);
    }

    public Optional<Board> findBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }
}
