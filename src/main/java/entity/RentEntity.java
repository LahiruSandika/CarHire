package entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Rent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 8)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "Customer_ID", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne()
    @JoinColumn(name = "Car_ID", nullable = false)
    private CarEntity carEntity;

    @Column(name = "From_Date", nullable = false)
    private Date fromDate;

    @Column(name = "To_Date", nullable = false)
    private Date toDate;

    @Column(name = "Daily_Rental", nullable = false)
    private Double dailyRental;

    @Column(name = "Total", nullable = false)
    private Double total;

    @Column(name = "Refundable_Deposit", nullable = false)
    private Double refundableDeposit;

    @Column(name = "Advance_Payment", nullable = false)
    private Double advancePayment;

    @Column(name = "is_Returned", nullable = false)
    private String isReturned;

    @Column(name = "Returned_On")
    private Date returnedOn;

    @Column(name = "Days_Overdue")
    private Integer daysOverdue;

    @Column(name = "Balance")
    private Double balance;

    @Column(name = "Created_By", nullable = false)
    private String createdBy;

    @Column(name = "Updated_By")
    private String updatedBy;
}
