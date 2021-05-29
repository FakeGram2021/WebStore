package tim6.inventorymanagement.article.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import tim6.inventorymanagement.container.AbstractContainerBaseTest;
import tim6.inventorymanagement.web.v1.dto.article.ArticleGetDTO;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static tim6.inventorymanagement.util.ArticleUtil.assertArticleContentsEqual;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/articleTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GetArticleByIdTest extends AbstractContainerBaseTest {

    private final String EXISTING_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7";
    private final String NON_EXISTING_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb8";
    private final String INVALID_ARTICLE_ID = "invalidUUID";
    @Autowired private TestRestTemplate testRestTemplate;
    @LocalServerPort private int port;

    @Test
    public void testGetArticleByValidIdReturnsSuccessful() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ResponseEntity<ArticleGetDTO> response = this.sendGetRequest(apiEndpoint);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetArticleByValidIdReturnsBody() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ResponseEntity<ArticleGetDTO> response = this.sendGetRequest(apiEndpoint);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetArticleByValidIdReturnsValidContent() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ResponseEntity<ArticleGetDTO> response = this.sendGetRequest(apiEndpoint);

        assertArticleContentsEqual(
                response.getBody(),
                "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7",
                10,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt S",
                10,
                0);
    }

    @Test
    public void testGetArticleByNonExistingIdReturnsNotFound() throws Exception {
        URI apiEndpoint = this.buildUri(this.NON_EXISTING_ARTICLE_ID);
        ResponseEntity<ArticleGetDTO> response = this.sendGetRequest(apiEndpoint);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetArticleByInvalidIdReturnsBadRequest() throws Exception {
        URI apiEndpoint = this.buildUri(this.INVALID_ARTICLE_ID);
        ResponseEntity<ArticleGetDTO> response = this.sendGetRequest(apiEndpoint);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private URI buildUri(String id) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                        String.format("http://localhost:%d/api/articles/%s", this.port, id));
        return builder.build().encode().toUri();
    }

    private ResponseEntity<ArticleGetDTO> sendGetRequest(URI apiEndpoint) {
        return this.testRestTemplate.getForEntity(apiEndpoint, ArticleGetDTO.class);
    }
}
