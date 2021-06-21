package tim6.ordering.adapter.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim6.ordering.adapter.http.dto.OrderDTO;
import tim6.ordering.adapter.http.dto.OrderItemDTO;
import tim6.ordering.domain.model.Article;
import tim6.ordering.domain.model.Order;
import tim6.ordering.domain.service.ArticleService;
import tim6.ordering.domain.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static tim6.ordering.adapter.http.mapper.OrderMapper.toOrder;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/order")
public class OrderController {

    private final ArticleService articleService;
    private final OrderService orderService;

    @Autowired
    public OrderController(
            final ArticleService articleService,
            final OrderService orderService
    ) {
        this.articleService = articleService;
        this.orderService = orderService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> makeAnOrder(@Valid @RequestBody OrderDTO orderDTO) {
        final List<UUID> articleIds = orderDTO
                .getOrderItems()
                .stream()
                .map(OrderItemDTO::getArticleId)
                .collect(Collectors.toList());
        final Map<UUID, Article> persistedArticle = articleService.findArticlesByIds(articleIds);
        final Order order = toOrder(orderDTO, persistedArticle);
        orderService.makeAnOrder(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
