package tim6.inventorymanagement.web.v1.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePostDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotNull
    @Min(value = 0, message = "Price cannot be less than 0")
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = "Amount in stock cannot be less than 0")
    private int amountInStock;

    private String imageUrl;
}
