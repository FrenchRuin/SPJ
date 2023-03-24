package com.example.spj.service;

import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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


    private static final String KARLO_API_URL = "https://api.kakaobrain.com/v1/inference/karlo/t2i";
    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    private static final String KAKAO_API_KEY = "bb67167c460f9277d6a14292f196b274";
    private static final String NAVER_CLIENT_ID = "0QJhbYw2IhHIs78ihpxV";
    private static final String NAVER_SECRET = "LSz5CRvuF4";

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
    public String getKarloImage(String requested) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "KakaoAK "+ KAKAO_API_KEY);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text",getPapago(requested));
        jsonObject.put("batch_size",1);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("prompt",jsonObject);

        HttpEntity<String> request = new HttpEntity<>(jsonObject1.toString(),httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(KARLO_API_URL,request,String.class);

        /* ResponseEntity의 경우에는 Json Object로 바로 변환이 가능 // Parser 필요 없음 */
        JSONObject result = new JSONObject(response.getBody());

        JSONArray resultArray = (JSONArray) result.get("images");

        return resultArray.getJSONObject(0).get("image").toString();
    }

    /* PAPAGO API Section */
    public String getPapago(String requested){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.add("X-Naver-Client-Secret", NAVER_SECRET);

        JSONObject object = new JSONObject();
        object.put("source", "ko");
        object.put("target", "en");
        object.put("text", requested);
        HttpEntity<String> request = new HttpEntity<>(object.toString(),headers);

        ResponseEntity<String> response = restTemplate.postForEntity(PAPAGO_API_URL, request,String.class);

        JSONObject result = new JSONObject(response.getBody());
        JSONObject obj = (JSONObject) result.get("message");
        JSONObject obj1 = (JSONObject) obj.get("result");
        return obj1.get("translatedText").toString();
    }
}
