package ezstub_backend.client;

import ezstub_backend.dto.OCRResponseDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Component
public class OCRClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public OCRResponseDTO scanReceipt(File file) {

        String url = "http://127.0.0.1:8000/ocr/receipt";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        ResponseEntity<OCRResponseDTO> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        OCRResponseDTO.class
                );

        return response.getBody();
    }
}