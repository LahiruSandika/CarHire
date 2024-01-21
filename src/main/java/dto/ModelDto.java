package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelDto {
    private String model;
    private String category;
    private String brand;
    private String createdBy;
    private String updatedBy;
}
