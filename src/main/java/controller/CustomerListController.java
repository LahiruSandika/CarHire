package controller;

import dto.CustomerDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.CustomerReport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerListController {

    public AnchorPane formNode;
    public TableView customerTable;
    public Button deleteBtn;
    public Button updateBtn;
    private static CustomerDto selectedDto;
    private CustomerService customerService;
    private CustomerReport customerReport;

    public CustomerListController() {
        customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
        customerTable = new TableView();
        loadTable();
    }

    @FXML
    public void initialize() {
        loadTable();
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    public void newBtnOnAction(ActionEvent actionEvent) {
        try {
            HBox formNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
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

    public void updateBtnOnAction(ActionEvent actionEvent) {
        try {
            HBox formNode = FXMLLoader.load(this.getClass().getResource("/view/customer_update_form.fxml"));
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

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        try {
            CustomerDto customerDto = getTableSelection();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer?");
            alert.setHeaderText("Confirm to delete entry");
            alert.setContentText("Customer ID: " + customerDto.getId() + "\nName: " + customerDto.getFirstName() + " " + customerDto.getLastName());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = customerService.deleteCustomer(customerDto.getId());
                reload();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void loadTable() {
        customerTable.getColumns().clear();

        TableColumn customerId = new TableColumn("ID");
        customerId.prefWidthProperty().bind(customerTable.widthProperty().divide(10).subtract(2));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn name = new TableColumn("Name");
        name.prefWidthProperty().bind(customerTable.widthProperty().divide(10).multiply(3));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn nic = new TableColumn("NIC");
        nic.prefWidthProperty().bind(customerTable.widthProperty().divide(5));
        nic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        TableColumn address = new TableColumn("Address");
        address.prefWidthProperty().bind(customerTable.widthProperty().divide(10).multiply(3));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn renting = new TableColumn("Renting");
        renting.prefWidthProperty().bind(customerTable.widthProperty().divide(10));
        renting.setCellValueFactory(new PropertyValueFactory<>("renting"));

        customerTable.getColumns().addAll(customerId, name, nic, address, renting);

        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        for(CustomerDto customerDto : customerDtos) {
            customerTable.getItems().add(new CustomerTableDataModel(
                    customerDto.getId(),
                    customerDto.getTitle() + ". " + customerDto.getFirstName() + " " + customerDto.getLastName(),
                    customerDto.getNic(),
                    customerDto.getAddress() + ", " + customerDto.getCity() + ", " + customerDto.getProvince(),
                    customerDto.getRenting()
            ));
        }
    }

    public static CustomerDto getTableSelection () {
        return selectedDto;
    }

    public void reload() throws IOException {
        this.formNode.getChildren().clear();
        this.formNode.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/customer_list.fxml")));
    }

    public void customerTableOnMouseClicked(MouseEvent mouseEvent) {
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);

        CustomerTableDataModel customerTableDataModel = (CustomerTableDataModel) customerTable.getSelectionModel().getSelectedItem();
        if(customerTableDataModel != null) {
            selectedDto = customerService.getCustomer(customerTableDataModel.getCustomerId());

            if (mouseEvent.getClickCount() == 2) {
                try{
                    Stage stage = new Stage();
                    Parent node = FXMLLoader.load(this.getClass().getResource("/view/customer_detail.fxml"));

                    Scene scene = new Scene(node);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        }
    }

    public void printBtnOnAction(ActionEvent actionEvent) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Print Customer Details");
        dialog.setHeaderText("Select the required filter criteria");

        ToggleGroup group = new ToggleGroup();
        RadioButton all = new RadioButton("All Customers");
        RadioButton province = new RadioButton("Filter by Province");
        Label provinceLabel = new Label("Province: ");
        ComboBox provinceComboBox = new ComboBox();
        provinceComboBox.getItems().clear();
        provinceComboBox.getItems().addAll("Central", "Eastern", "North Central", "North Western", "Northern", "Sabaragamuwa", "Southern", "Uva", "Western");
        provinceComboBox.getSelectionModel().select(0);
        RadioButton rent = new RadioButton("Filter by Rent Status");
        Label rentLabel = new Label("Renting: ");
        ComboBox rentComboBox = new ComboBox();
        rentComboBox.getItems().clear();
        rentComboBox.getItems().addAll("Yes", "No");
        rentComboBox.getSelectionModel().select(0);
        group.getToggles().addAll(all, province, rent);
        ButtonType print = new ButtonType("Print");

        dialog.getDialogPane().getButtonTypes().addAll(print, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        gridPane.add(all, 0, 0);
        gridPane.add(province, 0, 1);
        gridPane.add(provinceLabel, 1, 1);
        gridPane.add(provinceComboBox, 2, 1);
        gridPane.add(rent, 0, 2);
        gridPane.add(rentLabel, 1, 2);
        gridPane.add(rentComboBox, 2, 2);
        dialog.getDialogPane().setContent(gridPane);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(all)) {
                rentComboBox.setDisable(true);
                provinceComboBox.setDisable(true);
            } else if (newValue.equals(province)) {
                rentComboBox.setDisable(true);
                provinceComboBox.setDisable(false);
            } else if (newValue.equals(rent)) {
                rentComboBox.setDisable(false);
                provinceComboBox.setDisable(true);
            }
        });

        all.setSelected(true);

        Optional<ButtonType> result = dialog.showAndWait();
        ButtonType buttonType = result.orElse(ButtonType.CANCEL);

        if(buttonType == print && all.isSelected()) {
            printAll();
        } else if (buttonType == print && province.isSelected()) {
            printFilteredByProvince((String) provinceComboBox.getSelectionModel().getSelectedItem());
        } else if (buttonType == print && rent.isSelected()) {
            printFilteredByRent((String) rentComboBox.getSelectionModel().getSelectedItem());
        }
    }

    public void printAll() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String filePath = "src/main/resources/report/customer_report.jrxml";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("filter", "Showing All Customers");

            List<CustomerDto> customerDtos = customerService.getAllCustomers();
            List<CustomerReport> list = new ArrayList<>();
            for(CustomerDto customerDto : customerDtos) {
                CustomerReport entry = new CustomerReport(
                        customerDto.getId(),
                        customerDto.getTitle() + ". " + customerDto.getFirstName() + " " + customerDto.getLastName(),
                        customerDto.getNic(),
                        customerDto.getAddress() + ", " + customerDto.getCity() + ", " + customerDto.getProvince(),
                        customerDto.getPhone().get(0) + "\n" + customerDto.getPhone().get(1),
                        customerDto.getEmail(),
                        customerDto.getRenting()
                );
                list.add(entry);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(print, "Reports/Customer Reports/Customer Report " + sdf.format(new Date()) +".pdf");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printFilteredByProvince(String province) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String filePath = "src/main/resources/report/customer_report.jrxml";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("filter", "Filtered by Province: " + province);

            List<CustomerDto> customerDtos = customerService.getAllCustomers();
            List<CustomerReport> list = new ArrayList<>();
            for(CustomerDto customerDto : customerDtos) {
                if(customerDto.getProvince().equals(province)) {
                    CustomerReport entry = new CustomerReport(
                            customerDto.getId(),
                            customerDto.getTitle() + ". " + customerDto.getFirstName() + " " + customerDto.getLastName(),
                            customerDto.getNic(),
                            customerDto.getAddress() + ", " + customerDto.getCity() + ", " + customerDto.getProvince(),
                            customerDto.getPhone().get(0) + "\n" + customerDto.getPhone().get(1),
                            customerDto.getEmail(),
                            customerDto.getRenting()
                    );
                    list.add(entry);
                }
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(print, "Reports/Customer Reports/Customer Report Filtered by Province(" + province + ") " + sdf.format(new Date()) +".pdf");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printFilteredByRent(String rent) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String filePath = "src/main/resources/report/customer_report.jrxml";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("filter", "Filtered by Rent: " + rent);

            List<CustomerDto> customerDtos = customerService.getAllCustomers();
            List<CustomerReport> list = new ArrayList<>();
            for(CustomerDto customerDto : customerDtos) {
                if(customerDto.getRenting().equals(rent)) {
                    CustomerReport entry = new CustomerReport(
                            customerDto.getId(),
                            customerDto.getTitle() + ". " + customerDto.getFirstName() + " " + customerDto.getLastName(),
                            customerDto.getNic(),
                            customerDto.getAddress() + ", " + customerDto.getCity() + ", " + customerDto.getProvince(),
                            customerDto.getPhone().get(0) + "\n" + customerDto.getPhone().get(1),
                            customerDto.getEmail(),
                            customerDto.getRenting()
                    );
                    list.add(entry);
                }
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(print, "Reports/Customer Reports/Customer Report Filtered by Rent(" + rent + ") " + sdf.format(new Date()) +".pdf");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public class CustomerTableDataModel {
        private String customerId;
        private String name;
        private String nic;
        private String address;
        private String renting;
    }
}
