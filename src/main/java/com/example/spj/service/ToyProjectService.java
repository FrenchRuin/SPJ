package com.example.spj.service;

import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToyProjectService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private static final String API_URL = "https://api.kakaobrain.com/v1/inference/karlo/t2i";
    private static final String REST_API_KEY = "bb67167c460f9277d6a14292f196b274";

    /* Board Service Section */

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


    /* Karlo API Section */

    public void getKarloImage() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "KakaoAK "+ REST_API_KEY);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text","Tree");
        jsonObject.put("batch_size",1);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("prompt",jsonObject);

        HttpEntity<String> request = new HttpEntity<>(jsonObject1.toString(),httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL,request,String.class);

        /* ResponseEntity의 경우에는 Json Object로 바로 변환이 가능 // Parser 필요 없음 ^^ */
        JSONObject result = new JSONObject(response.getBody());
    }
}
