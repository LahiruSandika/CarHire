package entity;

import entity.embedded.CustomerAddress;
import entity.embedded.CustomerName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 8)
    private String id;

    @Column(name = "Title")
    private String title;

    private CustomerName name;

    @Column(name = "NIC", nullable = false, length = 5)
    private String nic;

    private CustomerAddress address;

    @ElementCollection
    @CollectionTable(
            name = "CustomerPhone",
            joinColumns = @JoinColumn(name = "Customer_ID")
    )
    private List<String> phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Renting")
    private String renting;

    @OneToMany(mappedBy = "customerEntity", targetEntity = RentEntity.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RentEntity> rentEntities;

    @Column(name = "Created_By")
    private String createdBy;

    @Column(name = "Updated_By")
    private String updatedBy;
}
