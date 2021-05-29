package tim6.inventorymanagement.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tim6.inventorymanagement.web.v1.dto.auth.LoginDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthUtil {

    public static String login(TestRestTemplate restTemplate, String username, String password) {
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(
                        "/api/auth", new LoginDTO(username, password), String.class);
        return responseEntity.getBody();
    }

    public static String login(MockMvc mockMvc, String username, String password) throws Exception {
        MvcResult responseEntity =
                mockMvc.perform(
                                post("/api/auth")
                                        .content(
                                                JsonUtil.asJsonString(
                                                        new LoginDTO(username, password)))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        return responseEntity.getResponse().getContentAsString();
    }

    public static HttpHeaders createHttpHeaders(String accessToken, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer: %s", accessToken));
        headers.setContentType(mediaType);
        return headers;
    }
}
