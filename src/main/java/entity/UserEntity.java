package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {
    @Id
    @Column(name = "Username", nullable = false, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 50)
    private String password;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "Mobile", nullable = false, length = 50)
    private String mobile;
}
