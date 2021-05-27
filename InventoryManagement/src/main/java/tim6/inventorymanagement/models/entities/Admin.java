package tim6.inventorymanagement.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
        indexes = {
            @Index(columnList = "email", unique = true),
            @Index(columnList = "username", unique = true)
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin implements Serializable {

    private static final long serialVersionUID = 3258231652305998659L;
    @Id @GeneratedValue private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Admin(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
