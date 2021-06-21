package tim6.ordering.adapter.http.mapper;

import tim6.ordering.adapter.http.dto.OrderDTO;
import tim6.ordering.adapter.http.dto.OrderItemDTO;
import tim6.ordering.domain.model.Article;
import tim6.ordering.domain.model.Order;
import tim6.ordering.domain.model.OrderItem;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toOrder(OrderDTO orderDTO, Map<UUID, Article> articles) {
        List<OrderItem> orderItems = orderDTO
                .getOrderItems()
                .stream()
                .map(orderItemDTO -> toOrderItem(orderItemDTO, articles))
                .collect(Collectors.toList());
        return Order
                .builder()
                .customer(orderDTO.getCustomer())
                .items(orderItems)
                .build();
    }

    private static OrderItem toOrderItem(OrderItemDTO orderItemDTO, Map<UUID, Article> articles) {
        if(!articles.containsKey(orderItemDTO.getArticleId())) {
            throw new EntityNotFoundException("Selected article does not exist");
        }
        return OrderItem
                .builder()
                .article(articles.get(orderItemDTO.getArticleId()))
                .quantity(orderItemDTO.getQuantity())
                .build();
    }


}
