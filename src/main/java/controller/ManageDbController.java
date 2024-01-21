package controller;

import dto.BrandDto;
import dto.CategoryDto;
import dto.ModelDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.*;
import service.ServiceFactory;
import service.custom.BrandService;
import service.custom.CategoryService;
import service.custom.ModelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageDbController {

    public AnchorPane formNode;
    public TableView categoryTable;
    public TableView brandTable;
    public TableView modelTable;
    public Button categoryUpdateBtn;
    public Button categoryDeleteBtn;
    public Button brandUpdateBtn;
    public Button brandDeleteBtn;
    public Button modelUpdateBtn;
    public Button modelDeleteBtn;
    public TextField categoryField;
    public TextField brandField;
    public TextField modelField;
    public Button modelCreateBtn;
    private CategoryService categoryService;
    private BrandService brandService;
    private ModelService modelService;
    private TableDataModel categoryTableDataModel;
    private TableDataModel brandTableDataModel;
    private TableDataModel modelTableDataModel;

    public ManageDbController() {
        categoryService = (CategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CATEGORY);
        brandService = (BrandService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.BRAND);
        modelService = (ModelService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.MODEL);

        categoryTable = new TableView();
        brandTable = new TableView();
        modelTable = new TableView();
    }

    @FXML
    public void initialize() {
        loadCategoryTable();
        loadBrandTable();
        loadModelTable();

        categoryTable.getSelectionModel().select(0);
        brandTable.getSelectionModel().select(0);

        categoryUpdateBtn.setDisable(true);
        categoryDeleteBtn.setDisable(true);
        brandUpdateBtn.setDisable(true);
        brandDeleteBtn.setDisable(true);
        modelCreateBtn.setDisable(true);
        modelUpdateBtn.setDisable(true);
        modelDeleteBtn.setDisable(true);
    }
    public void categoryCreateBtnOnAction(ActionEvent actionEvent) {
        try {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategory(categoryField.getText());
            categoryDto.setCreatedBy(LoginFormController.passUser());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new Category?");
            alert.setHeaderText("Confirm to create new entry");
            alert.setContentText("Category: " + categoryField.getText());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = categoryService.saveCategory(categoryDto);
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void categoryUpdateBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel tableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();
            CategoryDto previousDto = categoryService.getCategory(tableDataModel.getId());

            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Update Category Entry");
            textInputDialog.setHeaderText("Enter the new category");
            textInputDialog.setContentText("Current category: " + tableDataModel.getId());
            Optional<String> result = textInputDialog.showAndWait();
            String updatedCategory;
            if(result.isPresent()) {
                updatedCategory = textInputDialog.getEditor().getText();

                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setCategory(updatedCategory);
                categoryDto.setCreatedBy(previousDto.getCreatedBy());
                categoryDto.setUpdatedBy(LoginFormController.passUser());

                String resp = categoryService.updateCategory(categoryDto, previousDto.getCategory());
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void categoryDeleteBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel tableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Category?");
            alert.setHeaderText("Confirm to delete entry");
            alert.setContentText("Category: " + tableDataModel.getId());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = categoryService.deleteCategory(tableDataModel.getId());
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void brandCreateBtnOnAction(ActionEvent actionEvent) {
        try {
            BrandDto brandDto = new BrandDto();
            brandDto.setBrand(brandField.getText());
            brandDto.setCreatedBy(LoginFormController.passUser());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new Brand?");
            alert.setHeaderText("Confirm to create new entry");
            alert.setContentText("Brand: " + brandField.getText());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = brandService.saveBrand(brandDto);
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void brandUpdateBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel tableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();
            BrandDto previousDto = brandService.getBrand(tableDataModel.getId());

            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Update Brand Entry");
            textInputDialog.setHeaderText("Enter the new brand");
            textInputDialog.setContentText("Current brandy: " + tableDataModel.getId());
            Optional<String> result = textInputDialog.showAndWait();
            String updatedBrand;
            if(result.isPresent()) {
                updatedBrand = textInputDialog.getEditor().getText();

                BrandDto brandDto = new BrandDto();
                brandDto.setBrand(updatedBrand);
                brandDto.setCreatedBy(previousDto.getCreatedBy());
                brandDto.setUpdatedBy(LoginFormController.passUser());

                String resp = brandService.updateBrand(brandDto, previousDto.getBrand());
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void brandDeleteBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel tableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Brand?");
            alert.setHeaderText("Confirm to delete entry");
            alert.setContentText("Brand: " + tableDataModel.getId());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = brandService.deleteBrand(tableDataModel.getId());
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void modelCreateBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel categoryTableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();
            TableDataModel brandTableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();
            ModelDto modelDto = new ModelDto();
            modelDto.setModel(modelField.getText());
            modelDto.setBrand(brandTableDataModel.getId());
            modelDto.setCategory(categoryTableDataModel.getId());
            modelDto.setCreatedBy(LoginFormController.passUser());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new Model?");
            alert.setHeaderText("Confirm to create new entry");
            alert.setContentText("Category: " + categoryTableDataModel.getId() + "\nBrand: " + brandTableDataModel.getId() + "\nModel: " + modelField.getText());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = modelService.saveModel(modelDto);
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void modelUpdateBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel categoryTableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();
            TableDataModel brandTableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();
            TableDataModel modelTableDataModel = (TableDataModel) modelTable.getSelectionModel().getSelectedItem();
            ModelDto previousDto = modelService.getModel(modelTableDataModel.getId());

            Dialog dialog = new Dialog<>();
            dialog.setTitle("Update Model Entry");
            dialog.setHeaderText("Enter the new model details");

            Label categoryDetail = new Label("Previous Category: " + categoryTableDataModel.getId());
            ComboBox categoryComboBox = new ComboBox();
            categoryComboBox.setPromptText("Choose new category");
            categoryComboBox.getItems().clear();
            List<CategoryDto> categoryDtos = categoryService.getAllCategories();
            ArrayList<String> categories = new ArrayList<>();
            for(CategoryDto categoryDto : categoryDtos) {
                categories.add(categoryDto.getCategory());
            }
            categoryComboBox.getItems().addAll(categories);

            Label brandDetail = new Label("Previous Brand: " + brandTableDataModel.getId());
            ComboBox brandComboBox = new ComboBox();
            brandComboBox.setPromptText("Choose new brand");
            brandComboBox.getItems().clear();
            List<BrandDto> carBrandDtos = brandService.getAllBrands();
            ArrayList<String> brands = new ArrayList<>();
            for(BrandDto brandDto : carBrandDtos) {
                brands.add(brandDto.getBrand());
            }
            brandComboBox.getItems().addAll(brands);

            Label modelDetail = new Label("Previous Model: " + modelTableDataModel.getId());
            TextField modelField = new TextField();
            modelField.setPromptText("Enter new model");

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));
            gridPane.add(categoryDetail, 0, 0);
            gridPane.add(categoryComboBox, 1, 0);
            gridPane.add(brandDetail, 0, 1);
            gridPane.add(brandComboBox, 1, 1);
            gridPane.add(modelDetail, 0, 2);
            gridPane.add(modelField, 1, 2);
            dialog.getDialogPane().setContent(gridPane);

            Optional<ButtonType> result = dialog.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                ModelDto modelDto = new ModelDto();
                modelDto.setCategory(String.valueOf(categoryComboBox.getSelectionModel().getSelectedItem()));
                modelDto.setBrand(String.valueOf(brandComboBox.getSelectionModel().getSelectedItem()));
                modelDto.setModel(modelField.getText());
                modelDto.setCreatedBy(previousDto.getCreatedBy());
                modelDto.setUpdatedBy(LoginFormController.passUser());

                String resp = modelService.updateModel(modelDto, previousDto.getModel());
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void modelDeleteBtnOnAction(ActionEvent actionEvent) {
        try {
            TableDataModel tableDataModel = (TableDataModel) modelTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Model?");
            alert.setHeaderText("Confirm to delete entry");
            alert.setContentText("Model: " + tableDataModel.getId());
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);

            if(buttonType == ButtonType.OK) {
                String resp = modelService.deleteModel(tableDataModel.getId());
                clear();
                new Alert(Alert.AlertType.INFORMATION, resp).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadCategoryTable() {
        categoryTable.getColumns().clear();

        TableColumn category = new TableColumn("Categories");
        category.prefWidthProperty().bind(categoryTable.widthProperty().subtract(2));
        category.setCellValueFactory(new PropertyValueFactory<>("id"));

        categoryTable.getColumns().addAll(category);

        categoryTable.getItems().add(new TableDataModel("-All-"));
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        for (CategoryDto categoryDto : categoryDtos) {
            categoryTable.getItems().add(new TableDataModel(categoryDto.getCategory()));
        }
    }

    public void loadBrandTable() {
        brandTable.getColumns().clear();

        TableColumn brand = new TableColumn("Brands");
        brand.prefWidthProperty().bind(brandTable.widthProperty().subtract(2));
        brand.setCellValueFactory(new PropertyValueFactory<>("id"));

        brandTable.getColumns().add(brand);

        brandTable.getItems().add(new TableDataModel("-All-"));
        List<BrandDto> brandDtos = brandService.getAllBrands();
        for (BrandDto brandDto : brandDtos) {
            brandTable.getItems().add(new TableDataModel(brandDto.getBrand()));
        }
    }

    public void loadModelTable() {
        modelTable.getColumns().clear();

        TableColumn model = new TableColumn("Models");
        model.prefWidthProperty().bind(brandTable.widthProperty().subtract(2));
        model.setCellValueFactory(new PropertyValueFactory<>("id"));

        modelTable.getColumns().add(model);

        List<ModelDto> modelDtos = modelService.getAllModels();
        for (ModelDto modelDto : modelDtos) {
            modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
        }
    }

    public void clear() throws IOException {
        AnchorPane formNode = FXMLLoader.load(this.getClass().getResource("/view/manage_db.fxml"));
        AnchorPane.setTopAnchor(formNode, 0.0);
        AnchorPane.setBottomAnchor(formNode, 0.0);
        AnchorPane.setLeftAnchor(formNode, 0.0);
        AnchorPane.setRightAnchor(formNode, 0.0);
        this.formNode.getChildren().clear();
        this.formNode.getChildren().add(formNode);
    }

    public void categoryTableOnMouseClicked(MouseEvent mouseEvent) {
        categoryTableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();
        brandTableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();

        if(categoryTableDataModel.getId() != "-All-") {
            categoryUpdateBtn.setDisable(false);
            categoryDeleteBtn.setDisable(false);
        } else {
            categoryUpdateBtn.setDisable(true);
            categoryDeleteBtn.setDisable(true);
        }

        if(brandTableDataModel.getId() == "-All-" && categoryTableDataModel.getId() == "-All-") {
            modelCreateBtn.setDisable(true);
            modelTable.getItems().clear();
            loadModelTable();
        } else if (brandTableDataModel.getId() == "-All-") {
            modelCreateBtn.setDisable(true);
            modelTable.getItems().clear();

            List<ModelDto> modelDtos = modelService.getAllModels();
            for (ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory() == categoryTableDataModel.getId()) {
                    modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
                }
            }
        } else if (categoryTableDataModel.getId() == "-All-") {
            modelCreateBtn.setDisable(true);
            modelTable.getItems().clear();

            List<ModelDto> modelDtos = modelService.getAllModels();
            for (ModelDto modelDto : modelDtos) {
                if(modelDto.getBrand() == brandTableDataModel.getId()) {
                    modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
                }
            }
        } else {
            modelCreateBtn.setDisable(false);
            modelTable.getItems().clear();

            List<ModelDto> modelDtos = modelService.getAllModels();
            for (ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory() == categoryTableDataModel.getId() && modelDto.getBrand() == brandTableDataModel.getId()) {
                    modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
                }
            }
        }
        modelTableDataModel = (TableDataModel) modelTable.getSelectionModel().getSelectedItem();

        if(modelTableDataModel != null) {
            modelUpdateBtn.setDisable(false);
            modelDeleteBtn.setDisable(false);
        } else {
            modelUpdateBtn.setDisable(true);
            modelDeleteBtn.setDisable(true);
        }
    }

    public void brandTableOnMouseClicked(MouseEvent mouseEvent) {
        categoryTableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();
        brandTableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();

        if(brandTableDataModel.getId() != "-All-") {
            brandUpdateBtn.setDisable(false);
            brandDeleteBtn.setDisable(false);
        } else {
            brandUpdateBtn.setDisable(true);
            brandDeleteBtn.setDisable(true);
        }


        if(brandTableDataModel.getId() == "-All-" && categoryTableDataModel.getId() == "-All-") {
            modelCreateBtn.setDisable(true);
            modelTable.getItems().clear();
            loadModelTable();
        } else if (categoryTableDataModel.getId() == "-All-") {
            modelCreateBtn.setDisable(true);
            modelTable.getItems().clear();

            List<ModelDto> modelDtos = modelService.getAllModels();
            for (ModelDto modelDto : modelDtos) {
                if(modelDto.getBrand() == brandTableDataModel.getId()) {
                    modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
                }
            }
        } else if (brandTableDataModel.getId() == "-All-") {
            modelCreateBtn.setDisable(true);
            modelTable.getItems().clear();

            List<ModelDto> modelDtos = modelService.getAllModels();
            for (ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory() == categoryTableDataModel.getId()) {
                    modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
                }
            }
        } else {
            modelCreateBtn.setDisable(false);
            modelTable.getItems().clear();

            List<ModelDto> modelDtos = modelService.getAllModels();
            for (ModelDto modelDto : modelDtos) {
                if(modelDto.getCategory() == categoryTableDataModel.getId() && modelDto.getBrand() == brandTableDataModel.getId()) {
                    modelTable.getItems().add(new TableDataModel(modelDto.getModel()));
                }
            }
        }

        modelTableDataModel = (TableDataModel) modelTable.getSelectionModel().getSelectedItem();

        if(modelTableDataModel != null) {
            modelUpdateBtn.setDisable(false);
            modelDeleteBtn.setDisable(false);
        } else {
            modelUpdateBtn.setDisable(true);
            modelDeleteBtn.setDisable(true);
        }
    }

    public void modelTableOnMouseClicked(MouseEvent mouseEvent) {
        categoryTableDataModel = (TableDataModel) categoryTable.getSelectionModel().getSelectedItem();
        brandTableDataModel = (TableDataModel) brandTable.getSelectionModel().getSelectedItem();
        modelTableDataModel = (TableDataModel) modelTable.getSelectionModel().getSelectedItem();

        modelUpdateBtn.setDisable(false);
        modelDeleteBtn.setDisable(false);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public class TableDataModel {
        private String id;
    }
}
