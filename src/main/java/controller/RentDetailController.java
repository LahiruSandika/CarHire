package controller;

import dto.RentDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

public class RentDetailController {

    public AnchorPane formNode;
    public Label rentIdLabel;
    public Label fromLabel;
    public Label totalLabel;
    public Label returnedOnLabel;
    public Label customerIdLabel;
    public Label toLabel;
    public Label refundableDepositLabel;
    public Label daysOverdueLabel;
    public Label carIdLabel;
    public Label dailyRentalLabel;
    public Label advancePaymentLabel;
    public Label balanceLabel;
    public Label createdByLabel;
    public Label updatedByLabel;

    public void initialize() {
        try {
            RentDto rentDto = RentListController.getTableSelection();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            rentIdLabel.setText(rentIdLabel.getText() + " " + rentDto.getRentId());
            customerIdLabel.setText(customerIdLabel.getText() + " " + rentDto.getCustomerId());
            carIdLabel.setText(carIdLabel.getText() + " " + rentDto.getCarId());
            fromLabel.setText(fromLabel.getText() + " " + sdf.format(rentDto.getFromDate()));
            toLabel.setText(toLabel.getText() + " " + sdf.format(rentDto.getToDate()));
            dailyRentalLabel.setText(dailyRentalLabel.getText() + " " + rentDto.getDailyRental());
            totalLabel.setText(totalLabel.getText() + " " + rentDto.getTotal());
            refundableDepositLabel.setText(refundableDepositLabel.getText() + " " + rentDto.getRefundableDeposit());
            advancePaymentLabel.setText(advancePaymentLabel.getText() + " " + rentDto.getAdvancePayment());
            returnedOnLabel.setText(returnedOnLabel.getText() + " " + (rentDto.getReturnedOn() == null ? "Not Returned" : sdf.format(rentDto.getReturnedOn())));
            daysOverdueLabel.setText(daysOverdueLabel.getText() + " " + rentDto.getDaysOverdue());
            balanceLabel.setText(balanceLabel.getText() + " " + rentDto.getBalance());
            createdByLabel.setText(createdByLabel.getText() + " " + rentDto.getCreatedBy());
            updatedByLabel.setText(updatedByLabel.getText() + " " + (rentDto.getUpdatedBy() == null ? "" : rentDto.getUpdatedBy()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void closeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) this.formNode.getScene().getWindow();
        stage.close();
    }
}
