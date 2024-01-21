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
import service.ServiceFactory;
import service.custom.CustomerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerUpdateFormController {
    public HBox formNode;
    public Label idLabel;
    public TextField nicField;
    public TextField firstNameField;
    public ComboBox titleComBox;
    public TextField lastNameField;
    public TextField streetField;
    public TextField stateField;
    public ComboBox provinceComBox;
    public TextField contactNumberField;
    public TextField emailField;
    private CustomerService customerService;
    private String errorMsg;

    public CustomerUpdateFormController() {
        customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
    }

    @FXML
    public void initialize() {
        titleComBox.getItems().clear();
        titleComBox.getItems().addAll("Mr", "Ms", "Mrs");

        provinceComBox.getItems().clear();
        provinceComBox.getItems().addAll("Central", "Eastern", "North Central", "North Western", "Northern", "Sabaragamuwa", "Southern", "Uva", "Western");

        loadFields();
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        try {
            CustomerDto customerDto = new CustomerDto();
            CustomerDto previousCustomerDto = CustomerListController.getTableSelection();

            errorMsg = validateData();
            if(errorMsg == null) {
                customerDto.setId(idLabel.getText());
                customerDto.setTitle(String.valueOf(titleComBox.getSelectionModel().getSelectedItem()));
                customerDto.setFirstName(firstNameField.getText());
                customerDto.setLastName(lastNameField.getText());
                customerDto.setNic(nicField.getText());
                customerDto.setAddress(streetField.getText());
                customerDto.setCity(stateField.getText());
                customerDto.setProvince(String.valueOf(provinceComBox.getSelectionModel().getSelectedItem()));
                List<String> phone = new ArrayList<>();
                phone.add(contactNumberField.getText());
                customerDto.setPhone(phone);
                customerDto.setEmail(emailField.getText());
                customerDto.setRenting(previousCustomerDto.getRenting());
                customerDto.setCreatedBy(previousCustomerDto.getCreatedBy());
                customerDto.setUpdatedBy(LoginFormController.passUser());

                String resp = customerService.updateCustomer(customerDto);
                new Alert(Alert.AlertType.INFORMATION, resp).show();
                loadList();
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

    public void loadFields() {
        CustomerDto previousCustomerDto = CustomerListController.getTableSelection();

        idLabel.setText(previousCustomerDto.getId());
        titleComBox.setValue(previousCustomerDto.getTitle());
        firstNameField.setText(previousCustomerDto.getFirstName());
        lastNameField.setText((previousCustomerDto.getLastName()));
        nicField.setText(previousCustomerDto.getNic());
        streetField.setText(previousCustomerDto.getAddress());
        stateField.setText(previousCustomerDto.getCity());
        provinceComBox.setValue(previousCustomerDto.getProvince());
        contactNumberField.setText(previousCustomerDto.getPhone().get(0) != null ? previousCustomerDto.getPhone().get(0) : "");
        emailField.setText(previousCustomerDto.getEmail());
    }

    public void loadList() throws IOException {
        AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/customer_list.fxml"));
        AnchorPane.setTopAnchor(formNode, 0.0);
        AnchorPane.setBottomAnchor(formNode, 0.0);
        AnchorPane.setLeftAnchor(formNode, 0.0);
        AnchorPane.setRightAnchor(formNode, 0.0);
        AnchorPane rootNode = (AnchorPane) this.formNode.getParent();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(formNode);
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
        } else if (stateField.getText() == "") {
            return "City not entered";
        } else if (provinceComBox.getSelectionModel().getSelectedItem() == null) {
            return "Province not entered";
        } else if (!(isContactNumberValid || contactNumberField.getText() == "")) {
            return "Invalid Contact Number";
        }else if (!(emailField.getText().contains("@") || emailField.getText() == "")) {
            return "Invalid email";
        } else {
            return null;
        }
    }
}
