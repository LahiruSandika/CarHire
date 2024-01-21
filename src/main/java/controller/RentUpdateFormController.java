package controller;

import dto.CarDto;
import dto.CustomerDto;
import dto.RentDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import service.ServiceFactory;
import service.custom.CarService;
import service.custom.CustomerService;
import service.custom.RentService;
import util.DateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class RentUpdateFormController {

    public HBox formNode;
    public TextField customerIdField;
    public TextField carIdField;
    public DatePicker fromDate;
    public DatePicker toDate;
    public Label dailyRentalLabel;
    public Label totalLabel;
    public TextField refundableDepoField;
    public TextField advancePaymentField;
    public DatePicker returnedOnDate;
    public Label idLabel;
    public Label customerIdStatusLabel;
    public Label carIdStatusLabel;
    public Label fromDateStatusLabel;
    public Label toDateStatusLabel;
    public Label daysOverdueLabel;
    public Label balanceLabel;
    private RentService rentService;
    private CustomerService customerService;
    private CarService carService;
    private String errorMsg;

    public RentUpdateFormController() {
        rentService = (RentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.RENT);
        customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
        carService = (CarService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CAR);
    }

    public void initialize() {
        loadFields();
    }

    public void customerIdOnKeyTyped(KeyEvent keyEvent) {
        CustomerDto customerDto = customerService.getCustomer(customerIdField.getText());

        if(customerDto != null) {
            if(customerDto.getRenting().equals("No")) {
                customerIdStatusLabel.setText(customerDto.getTitle() + ". " + customerDto.getFirstName() + " " + customerDto.getLastName());
                errorMsg = null;
            } else {
                customerIdStatusLabel.setText("Customer already renting");
                errorMsg = "Customer already renting";
            }
        } else {
            customerIdStatusLabel.setText("Invalid Customer ID");
            errorMsg = "Invalid Customer ID";
        }
    }

    public void carIdOnKeyTyped(KeyEvent keyEvent) {
        CarDto carDto = carService.getCar(carIdField.getText());

        if(carDto != null) {
            if (carDto.getAvailability().equals("Available")) {
                carIdStatusLabel.setText(carDto.getYear() + " " + carDto.getBrand() + " " + carDto.getModel());
                dailyRentalLabel.setText(String.valueOf(carDto.getDailyRental()));
                errorMsg = null;
            } else {
                carIdStatusLabel.setText("Car already rented");
                errorMsg = "Car already rented";
            }
        } else {
            carIdStatusLabel.setText("Invalid Car ID");
            errorMsg = "Invalid Car ID";
        }
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        try {
            RentDto rentDto = new RentDto();
            RentDto previousDto = RentListController.getTableSelection();

            if(errorMsg == null) {
                errorMsg = validateData();
            }

            if(errorMsg == null) {
                rentDto.setRentId(idLabel.getText());
                rentDto.setCustomerId(customerIdField.getText());
                rentDto.setCarId(carIdField.getText());
                rentDto.setFromDate(DateUtil.asDate(fromDate.getValue()));
                rentDto.setToDate(DateUtil.asDate(toDate.getValue()));
                rentDto.setDailyRental(Double.parseDouble(dailyRentalLabel.getText()));
                rentDto.setTotal(Double.parseDouble(totalLabel.getText()));
                rentDto.setRefundableDeposit(Double.parseDouble(refundableDepoField.getText()));
                rentDto.setAdvancePayment(Double.parseDouble(advancePaymentField.getText()));
                rentDto.setReturnedOn(returnedOnDate.getValue() == null ? null : DateUtil.asDate(returnedOnDate.getValue()));
                rentDto.setDaysOverdue(Integer.parseInt(daysOverdueLabel.getText()));
                rentDto.setBalance(Double.parseDouble(balanceLabel.getText()));

                if(rentDto.getReturnedOn() == null) {
                    rentDto.setIsReturned("Not Returned");
                } else {
                    rentDto.setIsReturned("Returned");
                }
                rentDto.setCreatedBy(previousDto.getCreatedBy());
                rentDto.setUpdatedBy(LoginFormController.passUser());

                String resp = rentService.updateRent(rentDto);
                new Alert(Alert.AlertType.INFORMATION, resp).show();
                loadList();
            } else {
                new Alert(Alert.AlertType.ERROR, errorMsg).show();
                errorMsg = null;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void cancelBtnOnAction(ActionEvent actionEvent) {
        try {
            loadList();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void loadList() throws IOException {
        AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/rent_list.fxml"));
        AnchorPane.setTopAnchor(formNode, 0.0);
        AnchorPane.setBottomAnchor(formNode, 0.0);
        AnchorPane.setLeftAnchor(formNode, 0.0);
        AnchorPane.setRightAnchor(formNode, 0.0);
        AnchorPane rootNode = (AnchorPane) this.formNode.getParent();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(formNode);
    }

    public void fromDateOnAction(ActionEvent actionEvent) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = sdf.parse(sdf.format(new Date()));
        System.out.println("Current date: " + currentDate);
        Date fromDate = DateUtil.asDate(this.fromDate.getValue());
        if(currentDate.before(fromDate) | currentDate.equals(fromDate)) {
            fromDateStatusLabel.setText("Valid Date");
        } else {
            fromDateStatusLabel.setText("Invalid Date");
        }

        if(this.fromDate != null && this.toDate != null && dailyRentalLabel != null) {
            LocalDate from = this.fromDate.getValue();
            LocalDate to = this.toDate.getValue();
            String days = String.valueOf(ChronoUnit.DAYS.between(from, to));
            totalLabel.setText(String.valueOf(Double.parseDouble(days) * Double.parseDouble(dailyRentalLabel.getText())));
        }
    }

    public void toDateOnAction(ActionEvent actionEvent) {
        Date fromDate = DateUtil.asDate(this.fromDate.getValue());
        Date toDate = DateUtil.asDate(this.toDate.getValue());
        if(fromDate.before(toDate)) {
            toDateStatusLabel.setText("Valid Date");
        } else {
            toDateStatusLabel.setText("Invalid Date");
        }

        if(this.fromDate != null && this.toDate != null && dailyRentalLabel != null) {
            LocalDate from = this.fromDate.getValue();
            LocalDate to = this.toDate.getValue();
            String days = String.valueOf(ChronoUnit.DAYS.between(from, to));
            totalLabel.setText(String.valueOf(Double.parseDouble(days) * Double.parseDouble(dailyRentalLabel.getText())));
        }
    }

    public void returnedOnDateOnAction(ActionEvent actionEvent) {
        LocalDate returnedOn = this.returnedOnDate.getValue();
        LocalDate toDate = this.toDate.getValue();
        LocalDate fromDate = this.fromDate.getValue();

        long overdueDays = ChronoUnit.DAYS.between(toDate, returnedOn);
        daysOverdueLabel.setText(overdueDays < 0 ? "0" : String.valueOf(overdueDays));

        Date returnedOnDate = DateUtil.asDate(returnedOn);
        Date toDateDate = DateUtil.asDate(toDate);

        if(returnedOnDate.before(toDateDate)) {
            long days = ChronoUnit.DAYS.between(fromDate, returnedOn);
            Double total = days * Double.parseDouble(dailyRentalLabel.getText());
            Double balance = total - Double.parseDouble(refundableDepoField.getText()) - Double.parseDouble(advancePaymentField.getText());
            balanceLabel.setText(String.valueOf(balance));
            totalLabel.setText(totalLabel.getText() + " (Returned Early -" +String.valueOf(Double.parseDouble(totalLabel.getText()) - total) + ")");
        } else {
            Double balance = (Double.parseDouble(daysOverdueLabel.getText()) * Double.parseDouble(dailyRentalLabel.getText()) * 1.2)
                    + RentListController.getTableSelection().getTotal()
                    - Double.parseDouble(refundableDepoField.getText())
                    - Double.parseDouble(advancePaymentField.getText());
            balanceLabel.setText(String.valueOf(balance));
            totalLabel.setText(String.valueOf(RentListController.getTableSelection().getTotal()));
        }
    }

    public void loadFields() {
        try {
            RentDto rentDto = RentListController.getTableSelection();

            dailyRentalLabel.setText(rentDto.getRentId());
            customerIdField.setText(rentDto.getCustomerId());
            carIdField.setText(rentDto.getCarId());
            fromDate.setValue(DateUtil.asLocalDate(rentDto.getFromDate()));
            toDate.setValue(DateUtil.asLocalDate(rentDto.getToDate()));
            dailyRentalLabel.setText(String.valueOf(rentDto.getDailyRental()));
            totalLabel.setText(String.valueOf(rentDto.getTotal()));
            refundableDepoField.setText(String.valueOf(rentDto.getRefundableDeposit()));
            advancePaymentField.setText(String.valueOf(rentDto.getAdvancePayment()));
            returnedOnDate.setValue(rentDto.getReturnedOn() == null ? null : DateUtil.asLocalDate(rentDto.getReturnedOn()));
            daysOverdueLabel.setText(rentDto.getDaysOverdue() == null ? "0" : String.valueOf(rentDto.getDaysOverdue()));
            balanceLabel.setText(rentDto.getBalance() == null ? "0.00" : String.valueOf(rentDto.getBalance()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public String validateData() {
        Long days;
        try {
            LocalDate from = fromDate.getValue();
            LocalDate to = toDate.getValue();
            days = ChronoUnit.DAYS.between(from, to);
        } catch (Exception e) {
            days = 0L;
        }
        //System.out.println(days);

        Boolean isDepositValid;
        try {
            Double.parseDouble(refundableDepoField.getText());
            isDepositValid = true;
        } catch (Exception e) {
            if(refundableDepoField.getText() == ""){
                isDepositValid = true;
            } else {
                isDepositValid = false;
            }
        }

        Boolean isAdvanceValid;
        try {
            Double.parseDouble(advancePaymentField.getText());
            isAdvanceValid = true;
        } catch (Exception e) {
            if(advancePaymentField.getText() == ""){
                isAdvanceValid = true;
            } else {
                isAdvanceValid = false;
            }
        }

        Boolean isReturnedDateValid;
        try {
            Date fromDate = DateUtil.asDate(this.fromDate.getValue());
            Date returnedOn = DateUtil.asDate(this.returnedOnDate.getValue());

            if(returnedOn.before(fromDate) | returnedOn.equals(fromDate)) {
                isReturnedDateValid = false;
            } else {
                isReturnedDateValid = true;
            }
        } catch (Exception e) {
            if(returnedOnDate.getValue() == null) {
                isReturnedDateValid = true;
            } else {
                isReturnedDateValid = false;
            }
        }

        if(customerIdField.getText() == "") {
            return "Customer ID not Entered";
        } else if (carIdField.getText() == "") {
            return "Car ID not Entered";
        } else if (fromDate.getValue() == null) {
            return "Rent From Date not entered";
        } else if (toDate.getValue() == null) {
            return "Rent To Date not entered";
        }else if (days > 30) {
            return "Rent duration exceeded. Maximum rent period is 30 days";
        } else if (!isDepositValid) {
            return "Invalid Refundable Deposit Entered";
        } else if (!isAdvanceValid) {
            return "Invalid Advance Payment Entered";
        } else if (!isReturnedDateValid) {
            return "Invalid Returned Date Entered";
        } else {
            return null;
        }
    }
}
