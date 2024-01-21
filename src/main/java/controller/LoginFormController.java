package controller;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.UserService;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class LoginFormController {

    @FXML
    private Button loginBtn;

    @FXML
    private Label loginLabel;

    @FXML
    private PasswordField passField;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField userField;

    private UserService userService;

    private static UserDto userDto;

    private static Stage stage;

    public LoginFormController() {
        userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.USER);
    }
    @FXML
    public void userFieldOnAction(ActionEvent actionEvent) {
        passField.requestFocus();
    }

    @FXML
    public void passFieldOnAction(ActionEvent actionEvent) {
        loginBtnOnAction(actionEvent);
    }

    public void loginBtnOnAction(ActionEvent actionEvent) {

        String inputUsername = userField.getText();
        String inputPassword = passField.getText();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputPassword.getBytes());
            byte [] digest = md.digest();
            String hashPassword = DatatypeConverter.printHexBinary(digest).toLowerCase();

            userDto = userService.getUser(inputUsername);

            if(userDto != null) {
                if(hashPassword.equals(userDto.getPassword())) {
                    stage = (Stage) this.rootNode.getScene().getWindow();
                    stage.close();

                    stage = new Stage();

                    Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/layout_view.fxml"));

                    Scene scene = new Scene(rootNode);
                    stage.setScene(scene);
                    stage.setTitle("CarHire");
                    stage.centerOnScreen();
                    stage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Incorrect credentials").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect credentials").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public static String passUser() {
        return userDto.getUsername();
    }

    public static Stage passStage() {return stage;}
}
