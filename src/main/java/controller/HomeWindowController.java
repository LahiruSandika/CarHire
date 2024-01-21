package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeWindowController {

    public AnchorPane rootNode;
    public AnchorPane formNode;
    public Label userLabel;

    @FXML
    public void initialize() {
        try {
            userLabel.setText(userLabel.getText() + " " + LoginFormController.passUser());

            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/rent_list.fxml"));
            AnchorPane.setTopAnchor(formNode, 0.0);
            AnchorPane.setBottomAnchor(formNode, 0.0);
            AnchorPane.setLeftAnchor(formNode, 0.0);
            AnchorPane.setRightAnchor(formNode, 0.0);
            this.formNode.getChildren().clear();
            this.formNode.getChildren().add(formNode);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void rentBtnOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/rent_list.fxml"));
            AnchorPane.setTopAnchor(formNode, 0.0);
            AnchorPane.setBottomAnchor(formNode, 0.0);
            AnchorPane.setLeftAnchor(formNode, 0.0);
            AnchorPane.setRightAnchor(formNode, 0.0);
            this.formNode.getChildren().clear();
            this.formNode.getChildren().add(formNode);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void customerBtnOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/customer_list.fxml"));
            AnchorPane.setTopAnchor(formNode, 0.0);
            AnchorPane.setBottomAnchor(formNode, 0.0);
            AnchorPane.setLeftAnchor(formNode, 0.0);
            AnchorPane.setRightAnchor(formNode, 0.0);
            this.formNode.getChildren().clear();
            this.formNode.getChildren().add(formNode);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void carBtnOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/car_list.fxml"));
            AnchorPane.setTopAnchor(formNode, 0.0);
            AnchorPane.setBottomAnchor(formNode, 0.0);
            AnchorPane.setLeftAnchor(formNode, 0.0);
            AnchorPane.setRightAnchor(formNode, 0.0);
            this.formNode.getChildren().clear();
            this.formNode.getChildren().add(formNode);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void manageDbBtnOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/manage_db.fxml"));
            AnchorPane.setTopAnchor(formNode, 0.0);
            AnchorPane.setBottomAnchor(formNode, 0.0);
            AnchorPane.setLeftAnchor(formNode, 0.0);
            AnchorPane.setRightAnchor(formNode, 0.0);
            this.formNode.getChildren().clear();
            this.formNode.getChildren().add(formNode);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void logoutBtnOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) this.formNode.getScene().getWindow();
            stage.close();

            stage = new Stage();

            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

            Scene scene = new Scene(rootNode);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
