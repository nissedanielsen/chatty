package chatty.chatty_ms.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HuggingFaceService {

    private final RestTemplate restTemplate;

    public HuggingFaceService() {
        this.restTemplate = new RestTemplate();
    }

    public String getResponseFromModel(String userMessage) {

        //TODO: REFACTOR EVERYTHING
        String url = "http://huggingface:5000/generate";

        String jsonRequest = "{\"input\": \"" + userMessage + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);


        String responseBody = response.getBody();

        return responseBody.substring(responseBody.indexOf("\"response\":\"") + 11, responseBody.indexOf("\"}", responseBody.indexOf("\"response\":\"")));

    }
}

