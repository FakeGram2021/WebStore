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
import tim6.inventorymanagement.web.advice.ValidationErrorResponse;
import tim6.inventorymanagement.web.advice.Violation;
import tim6.inventorymanagement.web.v1.dto.article.ArticleGetDTO;
import tim6.inventorymanagement.web.v1.dto.article.ArticlePostDTO;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tim6.inventorymanagement.util.ArticleUtil.assertArticleContentsEqual;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/articleTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CreateArticleTest extends AbstractContainerBaseTest {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin";

    private final String VALID_ARTICLE_NAME = "testArticle";
    private final String VALID_ARTICLE_DESCRIPTION = "testDescription";
    private final BigDecimal VALID_ARTICLE_PRICE = BigDecimal.valueOf(100);
    private final BigDecimal VALID_ARTICLE_PRICE_ZERO = BigDecimal.ZERO;
    private final BigDecimal INVALID_ARTICLE_PRICE_NEGATIVE = BigDecimal.valueOf(-100);
    private final int VALID_ARTICLE_STOCK = 100;
    private final int VALID_ARTICLE_STOCK_ZERO = 0;
    private final int INVALID_ARTICLE_STOCK_NEGATIVE = -100;
    private final String VALID_ARTICLE_TEST_IMAGE_URL = "testImageUrl";
    private final String DEFAULT_ARTICLE_IMAGE_URL =
            "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg";

    @Autowired private TestRestTemplate testRestTemplate;
    @LocalServerPort private int port;

    @Test
    public void testCreateArticle() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPostRequest(apiEndpoint, articleToCreate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertArticleContentsEqual(
                response.getBody(),
                this.VALID_ARTICLE_STOCK,
                this.VALID_ARTICLE_DESCRIPTION,
                this.VALID_ARTICLE_TEST_IMAGE_URL,
                this.VALID_ARTICLE_NAME,
                this.VALID_ARTICLE_PRICE,
                0);
    }

    @Test
    public void testCreateArticleIsCreated() throws Exception {
        URI postEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPostRequest(postEndpoint, articleToCreate, this.getAuthToken());
        UUID id = response.getBody().getId();

        URI getEndpoint = this.buildGetUri(id.toString());
        ResponseEntity<ArticleGetDTO> responsePostCreated = this.sendGetRequest(getEndpoint);

        assertArticleContentsEqual(
                responsePostCreated.getBody(),
                id,
                this.VALID_ARTICLE_STOCK,
                this.VALID_ARTICLE_DESCRIPTION,
                this.VALID_ARTICLE_TEST_IMAGE_URL,
                this.VALID_ARTICLE_NAME,
                this.VALID_ARTICLE_PRICE,
                0);
    }

    @Test
    public void testCreateArticleInvalidName() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        null,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPostRequestExpectingError(
                        apiEndpoint, articleToCreate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getViolations().get(0).getFieldName(), "name");
        assertEquals(response.getBody().getViolations().get(0).getMessage(), "Name is mandatory");
    }

    @Test
    public void testCreateArticleZeroPriceReturnsCreated() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE_ZERO,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPostRequest(apiEndpoint, articleToCreate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testCreateArticleInvalidPrice() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.INVALID_ARTICLE_PRICE_NEGATIVE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPostRequestExpectingError(
                        apiEndpoint, articleToCreate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getViolations().get(0).getFieldName(), "price");
        assertEquals(
                response.getBody().getViolations().get(0).getMessage(),
                "Price cannot be less than 0");
    }

    @Test
    public void testCreateArticleZeroStockReturnsCreated() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK_ZERO,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPostRequest(apiEndpoint, articleToCreate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testCreateArticleInvalidStock() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.INVALID_ARTICLE_STOCK_NEGATIVE,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPostRequestExpectingError(
                        apiEndpoint, articleToCreate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getViolations().get(0).getFieldName(), "amountInStock");
        assertEquals(
                response.getBody().getViolations().get(0).getMessage(),
                "Amount in stock cannot be less than 0");
    }

    @Test
    public void testCreateArticleMultipleInvalidArguments() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        "",
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.INVALID_ARTICLE_PRICE_NEGATIVE,
                        this.INVALID_ARTICLE_STOCK_NEGATIVE,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPostRequestExpectingError(
                        apiEndpoint, articleToCreate, this.getAuthToken());

        List<Violation> violations = response.getBody().getViolations();
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertTrue(
                violations.stream()
                        .anyMatch(
                                v ->
                                        v.getFieldName().equals("name")
                                                && v.getMessage().equals("Name is mandatory")));
        assertTrue(
                violations.stream()
                        .anyMatch(
                                v ->
                                        v.getFieldName().equals("price")
                                                && v.getMessage()
                                                        .equals("Price cannot be less than 0")));
        assertTrue(
                violations.stream()
                        .anyMatch(
                                v ->
                                        v.getFieldName().equals("amountInStock")
                                                && v.getMessage()
                                                        .equals(
                                                                "Amount in stock cannot be less than 0")));
    }

    @Test
    public void testCreateArticleNoImageReturnsDefaultImage() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        null);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPostRequest(apiEndpoint, articleToCreate, this.getAuthToken());

        assertArticleContentsEqual(
                response.getBody(),
                this.VALID_ARTICLE_STOCK,
                this.VALID_ARTICLE_DESCRIPTION,
                this.DEFAULT_ARTICLE_IMAGE_URL,
                this.VALID_ARTICLE_NAME,
                this.VALID_ARTICLE_PRICE,
                0);
    }

    @Test
    public void testCreateArticleBlankImageReturnsDefaultImage() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        "");
        ResponseEntity<ArticleGetDTO> response =
                this.sendPostRequest(apiEndpoint, articleToCreate, this.getAuthToken());

        assertArticleContentsEqual(
                response.getBody(),
                this.VALID_ARTICLE_STOCK,
                this.VALID_ARTICLE_DESCRIPTION,
                this.DEFAULT_ARTICLE_IMAGE_URL,
                this.VALID_ARTICLE_NAME,
                this.VALID_ARTICLE_PRICE,
                0);
    }

    @Test
    public void testCreateArticleNeedsAuthorization() throws Exception {
        URI apiEndpoint = this.buildPostUri();
        ArticlePostDTO articleToCreate =
                new ArticlePostDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL);
        ResponseEntity<ArticleGetDTO> response = this.sendPostRequest(apiEndpoint, articleToCreate);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    private URI buildPostUri() {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                        String.format("http://localhost:%d/api/articles/", this.port));
        return builder.build().encode().toUri();
    }

    private URI buildGetUri(String id) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                        String.format("http://localhost:%d/api/articles/%s", this.port, id));
        return builder.build().encode().toUri();
    }

    private ResponseEntity<ArticleGetDTO> sendGetRequest(URI apiEndpoint) {
        return this.testRestTemplate.getForEntity(apiEndpoint, ArticleGetDTO.class);
    }

    private ResponseEntity<ArticleGetDTO> sendPostRequest(
            URI apiEndpoint, ArticlePostDTO requestBody) {
        HttpEntity<ArticlePostDTO> entity = new HttpEntity<>(requestBody);

        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.POST, entity, ArticleGetDTO.class);
    }

    private ResponseEntity<ArticleGetDTO> sendPostRequest(
            URI apiEndpoint, ArticlePostDTO requestBody, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", token));

        HttpEntity<ArticlePostDTO> entity = new HttpEntity<>(requestBody, headers);

        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.POST, entity, ArticleGetDTO.class);
    }

    private ResponseEntity<ValidationErrorResponse> sendPostRequestExpectingError(
            URI apiEndpoint, ArticlePostDTO requestBody, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", token));

        HttpEntity<ArticlePostDTO> entity = new HttpEntity<>(requestBody, headers);

        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.POST, entity, ValidationErrorResponse.class);
    }

    private String getAuthToken() {
        return AuthUtil.login(this.testRestTemplate, this.ADMIN_USERNAME, this.ADMIN_PASSWORD);
    }
}
