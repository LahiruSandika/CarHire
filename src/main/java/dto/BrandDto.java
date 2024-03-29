package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BrandDto {
    private String brand;
    private String createdBy;
    private String updatedBy;
}
