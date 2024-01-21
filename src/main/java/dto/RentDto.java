package dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentDto {
    private String rentId;
    private String customerId;
    private String carId;
    private Date fromDate;
    private Date toDate;
    private Double dailyRental;
    private Double total;
    private Double refundableDeposit;
    private Double advancePayment;
    private String isReturned;
    private Date returnedOn;
    private Integer daysOverdue;
    private Double balance;
    private String createdBy;
    private String updatedBy;
}
