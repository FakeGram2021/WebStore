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
import tim6.inventorymanagement.web.v1.dto.article.ArticlePutDTO;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tim6.inventorymanagement.util.ArticleUtil.assertArticleContentsEqual;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/articleTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UpdateArticleTest extends AbstractContainerBaseTest {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin";

    private final String EXISTING_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7";
    private final String NON_EXISTING_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb8";
    private final String INVALID_ARTICLE_ID = "invalidUUID";

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
    public void testUpdateArticle() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPutRequest(apiEndpoint, articleToUpdate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertArticleContentsEqual(
                response.getBody(),
                this.EXISTING_ARTICLE_ID,
                this.VALID_ARTICLE_STOCK,
                this.VALID_ARTICLE_DESCRIPTION,
                this.VALID_ARTICLE_TEST_IMAGE_URL,
                this.VALID_ARTICLE_NAME,
                this.VALID_ARTICLE_PRICE,
                1);
    }

    @Test
    public void testUpdateArticleNoImageReturnsDefaultImage() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        null,
                        0);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPutRequest(apiEndpoint, articleToUpdate, this.getAuthToken());

        assertArticleContentsEqual(
                response.getBody(),
                this.EXISTING_ARTICLE_ID,
                this.VALID_ARTICLE_STOCK,
                this.VALID_ARTICLE_DESCRIPTION,
                this.DEFAULT_ARTICLE_IMAGE_URL,
                this.VALID_ARTICLE_NAME,
                this.VALID_ARTICLE_PRICE,
                1);
    }

    @Test
    public void testUpdateArticleNonExistingArticleReturnsNotFound() throws Exception {
        URI apiEndpoint = this.buildUri(this.NON_EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPutRequest(apiEndpoint, articleToUpdate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateArticleInvalidArticleIdReturnsBadRequest() throws Exception {
        URI apiEndpoint = this.buildUri(this.INVALID_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPutRequest(apiEndpoint, articleToUpdate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateArticleInvalidName() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        null,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPutRequestExpectingError(
                        apiEndpoint, articleToUpdate, this.getAuthToken());

        List<Violation> violations = response.getBody().getViolations();
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(violations.get(0).getFieldName(), "name");
        assertEquals(violations.get(0).getMessage(), "Name is mandatory");
    }

    @Test
    public void testUpdateArticlePriceZeroReturnsOk() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE_ZERO,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPutRequest(apiEndpoint, articleToUpdate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateArticleInvalidPrice() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.INVALID_ARTICLE_PRICE_NEGATIVE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPutRequestExpectingError(
                        apiEndpoint, articleToUpdate, this.getAuthToken());

        List<Violation> violations = response.getBody().getViolations();
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(violations.get(0).getFieldName(), "price");
        assertEquals(violations.get(0).getMessage(), "Price cannot be less than 0");
    }

    @Test
    public void testUpdateArticleStockZeroReturnsOk() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK_ZERO,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ArticleGetDTO> response =
                this.sendPutRequest(apiEndpoint, articleToUpdate, this.getAuthToken());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateArticleInvalidStock() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.INVALID_ARTICLE_STOCK_NEGATIVE,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ValidationErrorResponse> response =
                this.sendPutRequestExpectingError(
                        apiEndpoint, articleToUpdate, this.getAuthToken());

        List<Violation> violations = response.getBody().getViolations();
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(violations.get(0).getFieldName(), "amountInStock");
        assertEquals(violations.get(0).getMessage(), "Amount in stock cannot be less than 0");
    }

    @Test
    public void testUpdateArticleNeedsAuthorization() throws Exception {
        URI apiEndpoint = this.buildUri(this.EXISTING_ARTICLE_ID);
        ArticlePutDTO articleToUpdate =
                new ArticlePutDTO(
                        this.VALID_ARTICLE_NAME,
                        this.VALID_ARTICLE_DESCRIPTION,
                        this.VALID_ARTICLE_PRICE,
                        this.VALID_ARTICLE_STOCK,
                        this.VALID_ARTICLE_TEST_IMAGE_URL,
                        0);
        ResponseEntity<ArticleGetDTO> response = this.sendPutRequest(apiEndpoint, articleToUpdate);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
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

    private ResponseEntity<ArticleGetDTO> sendPutRequest(
            URI apiEndpoint, ArticlePutDTO requestBody) {
        HttpEntity<ArticlePutDTO> entity = new HttpEntity<>(requestBody);

        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.PUT, entity, ArticleGetDTO.class);
    }

    private ResponseEntity<ArticleGetDTO> sendPutRequest(
            URI apiEndpoint, ArticlePutDTO requestBody, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", token));

        HttpEntity<ArticlePutDTO> entity = new HttpEntity<>(requestBody, headers);

        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.PUT, entity, ArticleGetDTO.class);
    }

    private ResponseEntity<ValidationErrorResponse> sendPutRequestExpectingError(
            URI apiEndpoint, ArticlePutDTO requestBody, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", token));

        HttpEntity<ArticlePutDTO> entity = new HttpEntity<>(requestBody, headers);

        return this.testRestTemplate.exchange(
                apiEndpoint, HttpMethod.PUT, entity, ValidationErrorResponse.class);
    }

    private String getAuthToken() {
        return AuthUtil.login(this.testRestTemplate, this.ADMIN_USERNAME, this.ADMIN_PASSWORD);
    }
}
