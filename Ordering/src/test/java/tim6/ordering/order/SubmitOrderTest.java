package tim6.ordering.order;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import tim6.ordering.adapter.http.dto.OrderDTO;
import tim6.ordering.adapter.http.dto.OrderItemDTO;
import tim6.ordering.container.AbstractContainerBaseTest;
import tim6.ordering.domain.model.Customer;
import tim6.ordering.domain.model.Order;
import tim6.ordering.domain.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

import static tim6.ordering.util.CustomerUtil.generateCustomer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SubmitOrderTest extends AbstractContainerBaseTest {

    public static final String VALID_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb7";
    public static final String INVALID_ARTICLE_ID = "6b112cd2-6e12-48cd-aa13-2b9b381b8fb1";

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Autowired
    private OrderRepository orderRepository;

    @LocalServerPort
    private int port;

    @Test
    public void itSuccessfullySubmitsOrder() {
        //Given
        Customer customer = generateCustomer();
        OrderItemDTO orderItem = OrderItemDTO
                .builder()
                .articleId(UUID.fromString(VALID_ARTICLE_ID))
                .quantity(2)
                .build();
        OrderDTO orderDTO = OrderDTO
                .builder()
                .customer(customer)
                .orderItems(List.of(orderItem))
                .build();

        //When
        ResponseEntity<?> response = submitOrder(orderDTO);
        List<Order> orders = orderRepository.findAll();

        //Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        softly.assertThat(orders).hasSize(1);
        softly.assertAll();
    }

    @Test
    public void itFailsToSubmitOrderWhenArticleIdDoesNotExist() {
        //Given
        Customer customer = generateCustomer();
        OrderItemDTO orderItem = OrderItemDTO
                .builder()
                .articleId(UUID.fromString(INVALID_ARTICLE_ID))
                .quantity(2)
                .build();
        OrderDTO orderDTO = OrderDTO
                .builder()
                .customer(customer)
                .orderItems(List.of(orderItem))
                .build();

        //When
        ResponseEntity<?> response = submitOrder(orderDTO);

        //Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        softly.assertAll();
    }

    @Test
    public void itFailsToSubmitOrderWhenOrderedQuanityExceedsArticleQuantity() {
        //Given
        Customer customer = generateCustomer();
        OrderItemDTO orderItem = OrderItemDTO
                .builder()
                .articleId(UUID.fromString(VALID_ARTICLE_ID))
                .quantity(101)
                .build();
        OrderDTO orderDTO = OrderDTO
                .builder()
                .customer(customer)
                .orderItems(List.of(orderItem))
                .build();

        //When
        ResponseEntity<?> response = submitOrder(orderDTO);

        //Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        softly.assertAll();
    }

    private ResponseEntity<?> submitOrder(
        final OrderDTO requestBody
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderDTO> entity = new HttpEntity<>(requestBody, httpHeaders);

        return testRestTemplate.exchange(
            String.format("http://localhost:%d/api/v1/order/", this.port),
            HttpMethod.POST,
            entity,
            Void.class
        );
    }
}
