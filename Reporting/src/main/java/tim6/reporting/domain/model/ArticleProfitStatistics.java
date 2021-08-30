package tim6.reporting.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleProfitStatistics {

    private Article article;
    private BigDecimal profit;

    public ArticleProfitStatistics(Article article) {
        this.article = article;
        this.profit = BigDecimal.ZERO;
    }
}
