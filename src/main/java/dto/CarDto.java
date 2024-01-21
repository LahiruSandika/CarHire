package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CarDto {
    private String id;
    private String vehicleNumber;
    private String category;
    private String brand;
    private String model;
    private Integer year;
    private Double dailyRental;
    private String availability;
    private String createdBy;
    private String updatedBy;
}
