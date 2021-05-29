package tim6.inventorymanagement.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import tim6.inventorymanagement.web.v1.dto.auth.LoginDTO;

public class AuthUtil {

    public static String login(TestRestTemplate restTemplate, String username, String password) {
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(
                        "/api/auth", new LoginDTO(username, password), String.class);
        return responseEntity.getBody();
    }
}
