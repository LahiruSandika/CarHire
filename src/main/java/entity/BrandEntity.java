package entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Brand")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BrandEntity {
    @Id
    @Column(name = "Brand", length = 50, nullable = false)
    private String brand;

    @OneToMany(mappedBy = "brandEntity", targetEntity = CarEntity.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CarEntity> carEntities;

    @OneToMany(mappedBy = "brandEntity", targetEntity = ModelEntity.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ModelEntity> modelEntities;

    @Column(name = "Created_By")
    private String createdBy;

    @Column(name = "Updated_By")
    private String updatedBy;
}
