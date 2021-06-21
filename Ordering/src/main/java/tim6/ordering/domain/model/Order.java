package tim6.ordering.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer_order")
public class Order {

     @Id
     @GeneratedValue
     private UUID id;

     @CreationTimestamp
     @Temporal(TemporalType.TIMESTAMP)
     private Date creationTime;

     @Embedded
     private Customer customer;

     @OneToMany(cascade = CascadeType.ALL)
     private List<OrderItem> items;

     @Column(nullable = false)
     private BigDecimal price;

}
