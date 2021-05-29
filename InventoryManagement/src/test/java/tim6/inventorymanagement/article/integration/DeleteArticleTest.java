package tim6.inventorymanagement.article.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import tim6.inventorymanagement.container.AbstractContainerBaseTest;
import tim6.inventorymanagement.util.AuthUtil;
import tim6.inventorymanagement.web.v1.dto.article.ArticleGetDTO;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/articleTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DeleteArticleTest extends AbstractContainerBaseTest {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin";

    private final String EXISTING_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7";
    private final String NON_EXISTING_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb8";
    private final String INVALID_ARTICLE_ID = "invalidUUID";
    @Autowired private TestRestTemplate testRestTemplate;
    @LocalServerPort private int port;

    @Test
    public void testDeleteArticleByValidIdReturnsNoContent() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ResponseEntity<Void> response = this.sendDeleteRequest(apiEndpoint, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteArticleByValidIdDoesDelete() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ResponseEntity<Void> deleteResponse =
                this.sendDeleteRequest(apiEndpoint, this.getAuthToken());
        ResponseEntity<ArticleGetDTO> postDeleteResponse = this.sendGetRequest(apiEndpoint);

        assertEquals(postDeleteResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteArticleByValidIdNeedsAuthorization() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ResponseEntity<Void> response = this.sendDeleteRequest(apiEndpoint);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testDeleteArticleByNonExistingIdReturnsNotFound() throws Exception {
        URI apiEndpoint = this.buildUri(this.NON_EXISTING_ARTICLE_ID);
        ResponseEntity<Void> response = this.sendDeleteRequest(apiEndpoint, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteArticleByInvalidIdReturnsBadRequest() throws Exception {
        URI apiEndpoint = this.buildUri(this.INVALID_ARTICLE_ID);
        ResponseEntity<Void> response = this.sendDeleteRequest(apiEndpoint, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private URI buildUri(String id) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                        String.format("http://localhost:%d/api/articles/%s", this.port, id));
        return builder.build().encode().toUri();
    }

    private ResponseEntity<Void> sendDeleteRequest(URI apiEndpoint) {
        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }

    private ResponseEntity<ArticleGetDTO> sendGetRequest(URI apiEndpoint) {
        return this.testRestTemplate.getForEntity(apiEndpoint, ArticleGetDTO.class);
    }

    private ResponseEntity<Void> sendDeleteRequest(URI apiEndpoint, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", token));

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        return this.testRestTemplate.exchange(apiEndpoint, HttpMethod.DELETE, entity, Void.class);
    }

    private String getAuthToken() {
        return AuthUtil.login(this.testRestTemplate, this.ADMIN_USERNAME, this.ADMIN_PASSWORD);
    }
}
