package tim6.ordering.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = -6329825493438793874L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int amountInStock;

    private String imageUrl;

    @Version
    private long version;


    public Article(
            String name,
            String description,
            BigDecimal price,
            int amountInStock,
            String imageUrl
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
        this.imageUrl = imageUrl;
    }

    public Article(
            String name,
            String description,
            BigDecimal price,
            int amountInStock,
            String imageUrl,
            long version
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
        this.imageUrl = imageUrl;
        this.version = version;
    }
}
