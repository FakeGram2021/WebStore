package tim6.reporting.domain.service;

import org.springframework.stereotype.Service;
import tim6.reporting.domain.model.Order;
import tim6.reporting.domain.repository.OrderRepository;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findOrdersInGivenPeriod(Date from, Date to) {
        return orderRepository.findByCreationTimeBetween(from, to);
    }
}
