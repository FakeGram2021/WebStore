package tim6.inventorymanagement.web.v1.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleGetDTO {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int amountInStock;
    private String imageUrl;
    private long version;
}
