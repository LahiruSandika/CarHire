package entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryEntity {
    @Id
    @Column(name = "Category", length = 50, nullable = false)
    private String category;

    @OneToMany(mappedBy = "categoryEntity", targetEntity = CarEntity.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CarEntity> carEntities;

    @OneToMany(mappedBy = "categoryEntity", targetEntity = ModelEntity.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ModelEntity> modelEntities;

    @Column(name = "Created_By")
    private String createdBy;

    @Column(name = "Updated_By")
    private String updatedBy;
}
