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
public class CustomerAddress {
    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "City", nullable = false, length = 20)
    private String city;

    @Column(name = "Province", nullable = false)
    private String province;
}
