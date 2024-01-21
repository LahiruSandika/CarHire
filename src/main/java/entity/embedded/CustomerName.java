package entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class CustomerName {
    @Column(name = "First_Name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "Last_Name", nullable = false, length = 100)
    private String lastName;
}
