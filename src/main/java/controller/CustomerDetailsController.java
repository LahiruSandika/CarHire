package controller;

import dto.CustomerDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomerDetailsController {
    public AnchorPane formNode;
    public Label idLabel;
    public Label nameLabel;
    public Label nicLabel;
    public Label addressLabel;
    public Label emailLabel;
    public Label updatedByLabel;
    public Label createdByLabel;
    public Label phoneLabel;

    public void initialize() {
        try {
            CustomerDto customerDto = CustomerListController.getTableSelection();

            idLabel.setText(idLabel.getText() + " " + customerDto.getId());
            nameLabel.setText(nameLabel.getText() + " " + customerDto.getTitle() + ". " + customerDto.getFirstName() + " " + customerDto.getLastName());
            nicLabel.setText(nicLabel.getText() + " " + customerDto.getNic());
            emailLabel.setText(emailLabel.getText() + " " + customerDto.getEmail());
            addressLabel.setText(addressLabel.getText() + " " + customerDto.getAddress() + " " + customerDto.getCity() + " " + customerDto.getProvince());
            phoneLabel.setText(phoneLabel.getText() + " " + (customerDto.getPhone().get(0) == null ? "" : customerDto.getPhone().get(0)));
            createdByLabel.setText(createdByLabel.getText() + " " + customerDto.getCreatedBy());
            updatedByLabel.setText(updatedByLabel.getText() + " " + (customerDto.getUpdatedBy() == null ? "" : customerDto.getUpdatedBy()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void closeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) this.formNode.getScene().getWindow();
        stage.close();
    }
}
