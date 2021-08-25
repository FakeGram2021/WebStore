package tim6.reporting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldQuantityArticleStatistics {

    private Article article;
    private long soldQuantity;

    public SoldQuantityArticleStatistics(Article article) {
        this.article = article;
        this.soldQuantity = 0L;
    }
}
