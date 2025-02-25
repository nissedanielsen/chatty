package chatty.chatty_ms.service;

import chatty.chatty_ms.model.HuggingFaceRequest;
import chatty.chatty_ms.model.HuggingFaceResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HuggingFaceService {

    private final RestTemplate restTemplate;

    public HuggingFaceService() {
        this.restTemplate = new RestTemplate();
    }

    public String getResponseFromModel(HuggingFaceRequest request) {

        //TODO: REFACTOR EVERYTHING
        String url = "http://huggingface:5000/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HuggingFaceRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<HuggingFaceResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, HuggingFaceResponse.class);

        return response.getBody() != null ? response.getBody().getResponse() : null;


    }
}

