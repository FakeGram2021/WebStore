package tim6.ordering.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim6.ordering.domain.model.Article;
import tim6.ordering.domain.model.Order;
import tim6.ordering.domain.model.OrderItem;
import tim6.ordering.domain.repository.ArticleRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Map<UUID, Article> findArticlesByIds(List<UUID> articleIds) {
        return articleRepository
                .findAllById(articleIds)
                .stream()
                .collect(Collectors.toMap(Article::getId, Function.identity()));
    }

    public boolean checkArticleAvailability(Order order) {
        return order
                .getItems()
                .stream()
                .allMatch(orderItem -> orderItem.getQuantity() < orderItem.getArticle().getAmountInStock());
    }

    public void updateStocks(Order order) {
        for (OrderItem orderItem: order.getItems()) {
            int currentStock = orderItem.getArticle().getAmountInStock();
            int purchasedQuantity = orderItem.getQuantity();
            orderItem.getArticle().setAmountInStock(currentStock - purchasedQuantity);
        }
    }
}
