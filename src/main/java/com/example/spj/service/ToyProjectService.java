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

    public User saveBoard(Board board, User user) {
        User saveUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (saveUser != null) {
            board.setCreated(LocalDateTime.now());
            board.setUser(saveUser);
            boardRepository.save(board);
        }
        return saveUser;
    }

    public Optional<Board> findBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public void deleteBoard(Board board) {
        boardRepository.deleteById(board.getBoard_id());
    }
}
