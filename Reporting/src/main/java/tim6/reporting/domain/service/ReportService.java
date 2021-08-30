package tim6.reporting.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim6.reporting.domain.model.ArticleProfitStatistics;
import tim6.reporting.domain.model.SoldQuantityArticleStatistics;
import tim6.reporting.domain.model.Order;
import tim6.reporting.domain.model.OrderItem;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final OrderService orderService;

    @Autowired
    public ReportService (OrderService orderService) {
        this.orderService = orderService;
    }

    public List<SoldQuantityArticleStatistics> generateMostSoldArticlesReport(Date from, Date to, long topArticlesNumber) {
        List<Order> customerOrders = orderService.findOrdersInGivenPeriod(from, to);
        Map<UUID, SoldQuantityArticleStatistics> articleStatistics = new HashMap<>();
        for (Order customerOrder: customerOrders) {
            for(OrderItem orderItem: customerOrder.getItems()) {
                SoldQuantityArticleStatistics articleStatistic = articleStatistics.
                        getOrDefault(orderItem.getArticle().getId(), new SoldQuantityArticleStatistics(orderItem.getArticle()));
                long soldQuantity = articleStatistic.getSoldQuantity();
                articleStatistic.setSoldQuantity(soldQuantity + orderItem.getQuantity());
                articleStatistics.put(orderItem.getArticle().getId(), articleStatistic);
            }
        }
        return articleStatistics
                .values()
                .stream()
                .sorted(Comparator.comparingLong(SoldQuantityArticleStatistics::getSoldQuantity).reversed())
                .limit(topArticlesNumber)
                .collect(Collectors.toList());
    }

    public List<ArticleProfitStatistics> generateMostProfitableArticlesReport(Date from, Date to, long articleLimit) {
        List<Order> customerOrders = orderService.findOrdersInGivenPeriod(from, to);
        Map<UUID, ArticleProfitStatistics> articleStatistics = new HashMap<>();
        for (Order customerOrder: customerOrders) {
            for(OrderItem orderItem: customerOrder.getItems()) {
                ArticleProfitStatistics articleStatistic = articleStatistics.
                        getOrDefault(orderItem.getArticle().getId(), new ArticleProfitStatistics(orderItem.getArticle()));
                BigDecimal currentProfitFromArticle = articleStatistic.getProfit();
                BigDecimal articleOrderQuantity = BigDecimal.valueOf(orderItem.getQuantity());
                BigDecimal orderArticleProfit = orderItem.getArticle().getPrice().multiply(articleOrderQuantity);

                articleStatistic.setProfit(currentProfitFromArticle.add(orderArticleProfit));
                articleStatistics.put(orderItem.getArticle().getId(), articleStatistic);
            }
        }
        return articleStatistics
                .values()
                .stream()
                .sorted(Comparator.comparing(ArticleProfitStatistics::getProfit).reversed())
                .limit(articleLimit)
                .collect(Collectors.toList());
    }

}
