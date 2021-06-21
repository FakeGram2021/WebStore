package tim6.ordering.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim6.ordering.domain.model.Article;
import tim6.ordering.domain.model.Order;
import tim6.ordering.domain.model.OrderItem;
import tim6.ordering.domain.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ArticleService articleService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ArticleService articleService
    ) {
        this.orderRepository = orderRepository;
        this.articleService = articleService;
    }


    public void makeAnOrder(Order order) {
        if(!articleService.checkArticleAvailability(order)) {
            throw new IllegalArgumentException("Not enough stocks for selected items");
        }
        setOrdersPrice(order);
        articleService.updateStocks(order);
        orderRepository.save(order);
    }

    private void setOrdersPrice(Order order) {
        BigDecimal ordersPrice = order
                .getItems()
                .stream()
                .map(OrderItem::getArticle)
                .map(Article::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setPrice(ordersPrice);
    }
}
