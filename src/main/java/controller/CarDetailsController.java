package controller;

import dto.CarDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CarDetailsController {
    public Label idLabel;
    public Label categoryLabel;
    public Label modelLabel;
    public Label dailyRentalLabel;
    public Label vehicleNumberLabel;
    public Label brandLabel;
    public Label yearLabel;
    public Label availabilityLabel;
    public Label createdByLabel;
    public Label updatedByLabel;
    public AnchorPane formNode;

    public void initialize() {
        CarDto carDto = CarListController.getTableSelection();

        idLabel.setText(idLabel.getText() + " " + carDto.getId());
        vehicleNumberLabel.setText(vehicleNumberLabel.getText() + " " + carDto.getVehicleNumber());
        categoryLabel.setText(categoryLabel.getText() + " " + carDto.getCategory());
        brandLabel.setText(brandLabel.getText() + " " + carDto.getBrand());
        modelLabel.setText(modelLabel.getText() + " " + carDto.getModel());
        yearLabel.setText(yearLabel.getText() + " " + carDto.getYear());
        dailyRentalLabel.setText(dailyRentalLabel.getText() + " " + carDto.getDailyRental());
        availabilityLabel.setText(availabilityLabel.getText() + " " + carDto.getAvailability());
        createdByLabel.setText(categoryLabel.getText() + " " + carDto.getCreatedBy());
        updatedByLabel.setText(updatedByLabel.getText() + " " + (carDto.getUpdatedBy() == null ? "" : carDto.getUpdatedBy()));
    }

    public void closeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) this.formNode.getScene().getWindow();
        stage.close();
    }
}


