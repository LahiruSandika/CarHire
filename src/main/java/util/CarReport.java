package util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CarReport {
    private String id;
    private String vehicleNumber;
    private String category;
    private String brand;
    private String model;
    private Integer year;
    private Double dailyRental;
    private String availability;
}
