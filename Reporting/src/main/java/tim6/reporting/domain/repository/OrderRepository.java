package tim6.reporting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim6.reporting.domain.model.Order;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByCreationTimeBetween(Date creationTimeStart, Date creationTimeEnd);
}
