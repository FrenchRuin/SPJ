package com.example.spj.service;

import com.example.spj.entity.Board;
import com.example.spj.entity.User;
import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToyProjectService {

    @Autowired
    public ToyProjectService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    @Value("${kakao.karlo}")
    private static String KARLO_API_URL;

    @Value("${kakao.id}")
    private static String KAKAO_API_KEY;



    /*
     *
     * Board Service Section
     *
     */

    public List<Board> findAllBoard() {
        return boardRepository.findAll();
    }

    public User saveBoard(Board board, User user) {
        User saveUser = userRepository.findByUsername(user.getUsername());
        if (saveUser != null) {
            board.setCreatedTime(LocalDateTime.now());
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

    /*
     *
     * Karlo API Section
     *
     * */
    public String getKarloImage(String requested) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "KakaoAK " + KAKAO_API_KEY);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", getPapago("ko", "en", requested));
        jsonObject.put("batch_size", 1);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("prompt", jsonObject);

        HttpEntity<String> request = new HttpEntity<>(jsonObject1.toString(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(KARLO_API_URL, request, String.class);

        /* ResponseEntity의 경우에는 Json Object로 바로 변환이 가능 // Parser 필요 없음 */
        JSONObject result = new JSONObject(response.getBody());

        JSONArray resultArray = (JSONArray) result.get("images");

        return resultArray.getJSONObject(0).get("image").toString();
    }

    /* *
     *
     *
     * PAPAGO API Section
     *
     *  */

    public String getPapago(String source, String target, String requested) {

        String apiUrl = "https://openapi.naver.com/v1/papago/n2mt";
        String clientId = "0QJhbYw2IhHIs78ihpxV";
        String clientSecret = "LSz5CRvuF4";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);

        JSONObject requestObject = new JSONObject();
        requestObject.put("source", source);
        requestObject.put("target", target);
        requestObject.put("text", requested);
        HttpEntity<String> request = new HttpEntity<>(requestObject.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        JSONObject result = new JSONObject(response.getBody());
        log.info("result === {}" ,result);
        JSONObject message = (JSONObject) result.get("message");
        JSONObject translatedText = (JSONObject) message.get("result");
        return translatedText.get("translatedText").toString();
    }
}
