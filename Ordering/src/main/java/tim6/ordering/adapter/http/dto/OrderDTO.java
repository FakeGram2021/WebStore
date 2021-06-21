package tim6.ordering.adapter.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tim6.ordering.domain.model.Customer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @Valid
    @NotNull
    private Customer customer;

    @Valid
    @NotEmpty
    private List<OrderItemDTO> orderItems;
}
