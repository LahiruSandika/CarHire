package controller;

import dto.BrandDto;
import dto.CarDto;
import dto.CategoryDto;
import dto.ModelDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.BrandService;
import service.custom.CarService;
import service.custom.CategoryService;
import service.custom.ModelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarUpdateFormController {
    public HBox formNode;
    public Button updateBtn;
    public Button saveBtn;
    public Label idLabel;
    public TextField vehicleNumberField;
    public TextField yearField;
    public TextField dailyRentalField;
    public ComboBox categoryComBox;
    public ComboBox brandComBox;
    public ComboBox modelComBox;
    public CheckBox availabilityCheck;
    private CarService carService;
    private CategoryService categoryService;
    private BrandService brandService;
    private ModelService modelService;
    private String errorMsg;

    public CarUpdateFormController() {
        carService = (CarService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CAR);
        categoryService = (CategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CATEGORY);
        brandService = (BrandService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.BRAND);
        modelService = (ModelService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.MODEL);
    }

    public void initialize() {
        categoryComBox.getItems().clear();
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        ArrayList<String> categories = new ArrayList<>();
        for(CategoryDto categoryDto : categoryDtos) {
            categories.add(categoryDto.getCategory());
        }
        categoryComBox.getItems().addAll(categories);

        brandComBox.getItems().clear();
        List<BrandDto> brandDtos = brandService.getAllBrands();
        ArrayList<String> brands = new ArrayList<>();
        for(BrandDto brandDto : brandDtos) {
            brands.add(brandDto.getBrand());
        }
        brandComBox.getItems().addAll(brands);

        modelComBox.getItems().clear();
        List<ModelDto> modelDtos = modelService.getAllModels();
        ArrayList<String> models = new ArrayList<>();
        for(ModelDto modelDto : modelDtos) {
            models.add(modelDto.getModel());
        }
        modelComBox.getItems().addAll(models);

        loadFields();
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        try {
            CarDto carDto = new CarDto();
            CarDto previousDto = CarListController.getTableSelection();

            errorMsg = validateData();
            if(errorMsg == null) {
                carDto.setId(idLabel.getText());
                carDto.setVehicleNumber((vehicleNumberField.getText()));
                carDto.setCategory(String.valueOf(categoryComBox.getSelectionModel().getSelectedItem()));
                carDto.setBrand(String.valueOf(brandComBox.getSelectionModel().getSelectedItem()));
                carDto.setModel(String.valueOf(modelComBox.getSelectionModel().getSelectedItem()));
                carDto.setYear(Integer.parseInt(yearField.getText()));
                carDto.setDailyRental(Double.parseDouble(dailyRentalField.getText()));
                if(availabilityCheck.isSelected()) {
                    carDto.setAvailability("Available");
                } else {
                    carDto.setAvailability("Unavailable");
                }
                carDto.setCreatedBy(previousDto.getCreatedBy());
                carDto.setUpdatedBy(LoginFormController.passUser());
                String resp = carService.updateCar(carDto);
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
    public void loadFields() {
        CarDto previousDto = CarListController.getTableSelection();

        idLabel.setText(previousDto.getId());
        vehicleNumberField.setText(previousDto.getVehicleNumber());
        categoryComBox.setValue(previousDto.getCategory());
        brandComBox.setValue(previousDto.getBrand());
        modelComBox.setValue(previousDto.getModel());
        yearField.setText(String.valueOf(previousDto.getYear()));
        dailyRentalField.setText(String.valueOf(previousDto.getDailyRental()));
        if(previousDto.getAvailability().equals("Available")) {
            availabilityCheck.setSelected(true);
        } else {
            availabilityCheck.setSelected(false);
        }
    }

    public void loadList() throws IOException {
        if((Stage) this.formNode.getScene().getWindow() == LoginFormController.passStage()) {
            AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/car_list.fxml"));
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

    public String validateData() {
        Boolean isYearValid;
        try {
            Integer.parseInt(yearField.getText());
            isYearValid = true;
        } catch (Exception e) {
            isYearValid = false;
        }

        Boolean isDailyRentalValid;
        try {
            Double.parseDouble(dailyRentalField.getText());
            isDailyRentalValid = true;
        } catch (Exception e) {
            isDailyRentalValid = false;
        }

        if(vehicleNumberField.getText() == ""){
            return "Empty Vehicle Number";
        } else if (categoryComBox.getSelectionModel().getSelectedItem() == null) {
            return "Category not selected";
        } else if (brandComBox.getSelectionModel().getSelectedItem() == null) {
            return "Brand not selected";
        } else if (modelComBox.getSelectionModel().getSelectedItem() == null) {
            return "Model not selected";
        } else if (!isYearValid) {
            return "Invalid Year Entered";
        } else if (!isDailyRentalValid) {
            return "Invalid Daily Rental";
        }else {
            return null;
        }
    }

    public void categoryComBoxOnAction(ActionEvent actionEvent) {
        if(brandComBox.getSelectionModel().getSelectedItem() == null) {
            modelComBox.getItems().clear();
            List<ModelDto> modelDtos = modelService.getAllModels();
            ArrayList<String> models = new ArrayList<>();
            for(ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory().equals((String) categoryComBox.getSelectionModel().getSelectedItem())) {
                    models.add(modelDto.getModel());
                }
            }
            modelComBox.getItems().addAll(models);
        } else {
            modelComBox.getItems().clear();
            List<ModelDto> modelDtos = modelService.getAllModels();
            ArrayList<String> models = new ArrayList<>();
            for(ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory().equals((String) categoryComBox.getSelectionModel().getSelectedItem())
                        && modelDto.getBrand().equals((String) brandComBox.getSelectionModel().getSelectedItem())) {
                    models.add(modelDto.getModel());
                }
            }
            modelComBox.getItems().addAll(models);
        }
    }

    public void brandComBoxOnAction(ActionEvent actionEvent) {
        if(categoryComBox.getSelectionModel().getSelectedItem() == null) {
            modelComBox.getItems().clear();
            List<ModelDto> modelDtos = modelService.getAllModels();
            ArrayList<String> models = new ArrayList<>();
            for(ModelDto modelDto : modelDtos) {
                if(modelDto.getBrand().equals((String) brandComBox.getSelectionModel().getSelectedItem())) {
                    models.add(modelDto.getModel());
                }
            }
            modelComBox.getItems().addAll(models);
        } else {
            modelComBox.getItems().clear();
            List<ModelDto> modelDtos = modelService.getAllModels();
            ArrayList<String> models = new ArrayList<>();
            for(ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory().equals((String) categoryComBox.getSelectionModel().getSelectedItem())
                        && modelDto.getBrand().equals((String) brandComBox.getSelectionModel().getSelectedItem())) {
                    models.add(modelDto.getModel());
                }
            }
            modelComBox.getItems().addAll(models);
        }
    }

}
