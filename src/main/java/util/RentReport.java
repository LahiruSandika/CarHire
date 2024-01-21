package util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentReport {
    private String rentId;
    private String customerId;
    private String carId;
    private String fromDate;
    private String toDate;
    private String returnedOn;
    private Double dailyRental;
    private Double total;
}
