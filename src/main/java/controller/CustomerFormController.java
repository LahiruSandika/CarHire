package controller;

import dto.CustomerDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.CustomerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {
    public HBox formNode;
    public Label idLabel;
    public ComboBox titleComBox;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField nicField;
    public TextField streetField;
    public TextField cityField;
    public TextField contactNumberField;
    public ComboBox provinceComBox;
    public TextField emailField;

    private CustomerService customerService;
    private String errorMsg;

    public CustomerFormController() {
        customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
    }

    @FXML
    public void initialize() {
        titleComBox.getItems().clear();
        titleComBox.getItems().addAll("Mr", "Ms", "Mrs");

        provinceComBox.getItems().clear();
        provinceComBox.getItems().addAll("Central", "Eastern", "North Central", "North Western", "Northern", "Sabaragamuwa", "Southern", "Uva", "Western");

        idLabel.setText(generateId());
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        try {
            CustomerDto customerDto = new CustomerDto();

            errorMsg = validateData();
            if(errorMsg == null) {
                customerDto.setId(idLabel.getText());
                customerDto.setTitle(String.valueOf(titleComBox.getSelectionModel().getSelectedItem()));
                customerDto.setFirstName(firstNameField.getText());
                customerDto.setLastName(lastNameField.getText());
                customerDto.setNic(nicField.getText());
                customerDto.setAddress(streetField.getText());
                customerDto.setCity(cityField.getText());
                customerDto.setProvince(String.valueOf(provinceComBox.getSelectionModel().getSelectedItem()));
                List<String> phone = new ArrayList<>();
                phone.add(contactNumberField.getText());
                customerDto.setPhone(phone);
                customerDto.setEmail(emailField.getText());
                customerDto.setRenting("No");
                customerDto.setCreatedBy(LoginFormController.passUser());

                String resp = customerService.saveCustomer(customerDto);
                new Alert(Alert.AlertType.INFORMATION, resp).show();
                loadList();
            } else {
                new Alert(Alert.AlertType.ERROR, errorMsg).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void cancelBtnOnAction(ActionEvent actionEvent) {
        try {
            loadList();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void loadList() throws IOException {
        if((Stage) this.formNode.getScene().getWindow() == LoginFormController.passStage()) {
            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/customer_list.fxml"));
            AnchorPane.setTopAnchor(formNode, 0.0);
            AnchorPane.setBottomAnchor(formNode, 0.0);
            AnchorPane.setLeftAnchor(formNode, 0.0);
            AnchorPane.setRightAnchor(formNode, 0.0);
            AnchorPane rootNode = (AnchorPane) this.formNode.getParent();
            rootNode.getChildren().clear();
            rootNode.getChildren().add(formNode);
        } else {
            Stage stage = (Stage) this.formNode.getScene().getWindow();
            stage.close();
        }
    }

    public String generateId() {
        try {
            List<CustomerDto> customerDtos = customerService.getAllCustomers();
            CustomerDto customerDto = customerDtos.get(customerDtos.size() - 1);

            String prevId = customerDto.getId().substring(4);
            Integer incremetedId = Integer.parseInt(prevId) + 1;
            String id = "CUST" + String.format("%04d", incremetedId);
            return id;

        } catch (Exception e) {
            return "CUST0001";
        }
    }

    public String validateData() {
        Boolean isContactNumberValid;
        try {
            Integer.parseInt(contactNumberField.getText());
            isContactNumberValid = true;
        } catch (Exception e) {
            isContactNumberValid = false;
        }

        if(firstNameField.getText() == "") {
            return "First Name not entered";
        } else if (lastNameField.getText() == "") {
            return "Last Name not entered";
        } else if (nicField.getText() == "") {
            return "NIC not entered";
        } else if (streetField.getText() == "") {
            return "Address not entered";
        } else if (cityField.getText() == "") {
            return "City not entered";
        } else if (provinceComBox.getSelectionModel().getSelectedItem() == null) {
            return "Province not entered";
        } else if (!(isContactNumberValid || contactNumberField.getText() == "")) {
            return "Invalid Contact Number";
        } else if (!(emailField.getText().contains("@") || emailField.getText() == "")) {
            return "Invalid email";
        } else {
            return null;
        }
    }
}
