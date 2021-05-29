package tim6.inventorymanagement.util;

import org.hamcrest.Matchers;
import tim6.inventorymanagement.web.v1.dto.article.ArticleGetDTO;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ArticleUtil {

    public static void assertArticleContentsEqual(
            ArticleGetDTO article,
            String id,
            int amountInStock,
            String description,
            String imageUrl,
            String name,
            int price,
            long version) {
        assertEquals(article.getId(), UUID.fromString(id));
        assertEquals(article.getAmountInStock(), amountInStock);
        assertEquals(article.getDescription(), description);
        assertEquals(article.getImageUrl(), imageUrl);
        assertEquals(article.getName(), name);
        assertThat(article.getPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(price)));
        assertEquals(article.getVersion(), version);
    }

    public static void assertArticleContentsEqual(
            ArticleGetDTO article,
            String id,
            int amountInStock,
            String description,
            String imageUrl,
            String name,
            BigDecimal price,
            long version) {
        assertEquals(article.getId(), UUID.fromString(id));
        assertEquals(article.getAmountInStock(), amountInStock);
        assertEquals(article.getDescription(), description);
        assertEquals(article.getImageUrl(), imageUrl);
        assertEquals(article.getName(), name);
        assertThat(article.getPrice(), Matchers.comparesEqualTo(price));
        assertEquals(article.getVersion(), version);
    }

    public static void assertArticleContentsEqual(
            ArticleGetDTO article,
            UUID id,
            int amountInStock,
            String description,
            String imageUrl,
            String name,
            BigDecimal price,
            long version) {
        assertEquals(article.getId(), id);
        assertEquals(article.getAmountInStock(), amountInStock);
        assertEquals(article.getDescription(), description);
        assertEquals(article.getImageUrl(), imageUrl);
        assertEquals(article.getName(), name);
        assertThat(article.getPrice(), Matchers.comparesEqualTo(price));
        assertEquals(article.getVersion(), version);
    }

    public static void assertArticleContentsEqual(
            ArticleGetDTO article,
            int amountInStock,
            String description,
            String imageUrl,
            String name,
            BigDecimal price,
            long version) {
        assertEquals(article.getAmountInStock(), amountInStock);
        assertEquals(article.getDescription(), description);
        assertEquals(article.getImageUrl(), imageUrl);
        assertEquals(article.getName(), name);
        assertThat(article.getPrice(), Matchers.comparesEqualTo(price));
        assertEquals(article.getVersion(), version);
    }
}
