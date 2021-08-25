package tim6.reporting.reports;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import tim6.reporting.container.AbstractContainerBaseTest;
import tim6.reporting.domain.model.SoldQuantityArticleStatistics;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MostSoldReportTest extends AbstractContainerBaseTest {

    private final List<UUID> MOST_SOLD_ARTICLE_IDS = List.of(
            UUID.fromString("13d2b5e7-14f0-48f4-9a8f-ad69fe267e9f"),
            UUID.fromString("045474f0-0ecd-43b8-b172-b9324d8882a0"),
            UUID.fromString("240a9b16-27ad-4b5b-91ab-ade05196b693"),
            UUID.fromString("6b112cd2-6e12-48cd-aa13-2b9b381b8fb7"),
            UUID.fromString("165bb151-aa28-4170-9b52-7769b5f8fb1f")
    );

    private final List<Long> MOST_SOLD_ARTICLES_QUANTITIES = List.of(45L, 40L, 29L, 28L, 18L);

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void itVerifiesDataFromMostSoldArticleReport() {
        //When
        ResponseEntity<List<SoldQuantityArticleStatistics>> response = generatesReport();
        List<SoldQuantityArticleStatistics> articleStatistics =  response.getBody();

        //Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articleStatistics).extracting("article.id").hasSameElementsAs(MOST_SOLD_ARTICLE_IDS);
        softly.assertThat(articleStatistics).extracting("soldQuantity").hasSameElementsAs(MOST_SOLD_ARTICLES_QUANTITIES);
        softly.assertAll();
    }

    private ResponseEntity<List<SoldQuantityArticleStatistics>> generatesReport() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(httpHeaders);

        return testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/report/mostSold?from=01062021&to=30062021", this.port),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }
}
