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
import tim6.inventorymanagement.web.v1.dto.article.ArticlePage;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tim6.inventorymanagement.util.ArticleUtil.assertArticleContentsEqual;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/articleTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GetAllArticlesTest extends AbstractContainerBaseTest {

    private final int TOTAL_NUM_OF_ARTICLES = 7;
    private final int PAGE_SIZE = 5;

    @Autowired private TestRestTemplate testRestTemplate;
    @LocalServerPort private int port;

    @Test
    public void testGetAllArticles() throws Exception {
        URI apiEndpoint = this.buildUri();
        ResponseEntity<ArticlePage> response = this.sendGetRequest(apiEndpoint);
        ArticlePage page = response.getBody();
        List<ArticleGetDTO> articles = page.getContent();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(articles.size(), this.TOTAL_NUM_OF_ARTICLES);
        assertArticleContentsEqual(
                articles.get(0),
                "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7",
                10,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt S",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(1),
                "27b7c07b-115b-4343-8ff2-aed84785fe44",
                5,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt M",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(2),
                "045474f0-0ecd-43b8-b172-b9324d8882a0",
                3,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt L",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(3),
                "165bb151-aa28-4170-9b52-7769b5f8fb1f",
                0,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt XL",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(4),
                "6e9f4ec8-16f4-4f03-8ece-d8c38157c7e5",
                10,
                "Bluest of shirts",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt 1",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(5),
                "240a9b16-27ad-4b5b-91ab-ade05196b693",
                2,
                "Wow! Shirt, blue.",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt 2",
                50,
                0);

        assertArticleContentsEqual(
                articles.get(6),
                "13d2b5e7-14f0-48f4-9a8f-ad69fe267e9f",
                0,
                "Very well made blue shirt with long description",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt 3",
                20,
                0);
    }

    @Test
    public void testGetAllArticlesPage0() throws Exception {
        URI apiEndpoint = this.buildUri(0, this.PAGE_SIZE);
        ResponseEntity<ArticlePage> response = this.sendGetRequest(apiEndpoint);
        ArticlePage page = response.getBody();
        List<ArticleGetDTO> articles = page.getContent();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(articles.size(), this.PAGE_SIZE);
        assertArticleContentsEqual(
                articles.get(0),
                "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7",
                10,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt S",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(1),
                "27b7c07b-115b-4343-8ff2-aed84785fe44",
                5,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt M",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(2),
                "045474f0-0ecd-43b8-b172-b9324d8882a0",
                3,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt L",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(3),
                "165bb151-aa28-4170-9b52-7769b5f8fb1f",
                0,
                "Really nice blue shirt",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt XL",
                10,
                0);

        assertArticleContentsEqual(
                articles.get(4),
                "6e9f4ec8-16f4-4f03-8ece-d8c38157c7e5",
                10,
                "Bluest of shirts",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt 1",
                10,
                0);
    }

    @Test
    public void testGetAllArticlesPage1() throws Exception {
        URI apiEndpoint = this.buildUri(1, this.PAGE_SIZE);
        ResponseEntity<ArticlePage> response = this.sendGetRequest(apiEndpoint);
        ArticlePage page = response.getBody();
        List<ArticleGetDTO> articles = page.getContent();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(articles.size(), this.TOTAL_NUM_OF_ARTICLES - this.PAGE_SIZE);
        assertArticleContentsEqual(
                articles.get(0),
                "240a9b16-27ad-4b5b-91ab-ade05196b693",
                2,
                "Wow! Shirt, blue.",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt 2",
                50,
                0);

        assertArticleContentsEqual(
                articles.get(1),
                "13d2b5e7-14f0-48f4-9a8f-ad69fe267e9f",
                0,
                "Very well made blue shirt with long description",
                "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
                "Blue shirt 3",
                20,
                0);
    }

    private URI buildUri() {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                        String.format("http://localhost:%d/api/articles", this.port));
        return builder.build().encode().toUri();
    }

    private URI buildUri(int page, int pageSize) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                                String.format("http://localhost:%d/api/articles", this.port))
                        .queryParam("page", page)
                        .queryParam("size", pageSize);
        return builder.build().encode().toUri();
    }

    private ResponseEntity<ArticlePage> sendGetRequest(URI apiEndpoint) {
        return this.testRestTemplate.getForEntity(apiEndpoint, ArticlePage.class);
    }
}
