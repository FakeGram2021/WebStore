package tim6.ordering.util;

import tim6.ordering.domain.model.Customer;

public class CustomerUtil {

    public static Customer generateCustomer() {
        return Customer
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@email.com")
                .address("Address")
                .city("City")
                .phone("111-222")
                .zipcode("10000")
                .country("Serbia")
                .build();
    }
}
