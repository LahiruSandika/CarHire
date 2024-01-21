package service.custom.impl;

import dao.DaoFactory;
import dao.custom.BrandDao;
import dao.custom.CategoryDao;
import dao.custom.ModelDao;
import dto.ModelDto;
import entity.ModelEntity;
import org.hibernate.Transaction;
import service.custom.ModelService;

import java.util.ArrayList;
import java.util.List;

public class ModelServiceImpl implements ModelService {
    ModelDao modelDao = (ModelDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.MODEL);
    CategoryDao categoryDao = (CategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CATEGORY);
    BrandDao brandDao = (BrandDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BRAND);

    @Override
    public String saveModel(ModelDto modelDto) {
        Transaction transaction = session.beginTransaction();
        ModelEntity modelEntity = new ModelEntity();

        modelEntity.setModel(modelDto.getModel());
        modelEntity.setBrandEntity(brandDao.get(modelDto.getBrand(), session));
        modelEntity.setCategoryEntity(categoryDao.get(modelDto.getCategory(), session));
        modelEntity.setCreatedBy(modelDto.getCreatedBy());

        if(modelDao.save(modelEntity, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public String updateModel(ModelDto modelDto, String previousModel) {
        Transaction transaction = session.beginTransaction();
        ModelEntity modelEntity = new ModelEntity();

        modelEntity.setModel(modelDto.getModel());
        modelEntity.setBrandEntity(brandDao.get(modelDto.getBrand(), session));
        modelEntity.setCategoryEntity(categoryDao.get(modelDto.getCategory(), session));
        modelEntity.setCreatedBy(modelDto.getCreatedBy());
        modelEntity.setUpdatedBy(modelDto.getUpdatedBy());

        if(modelDao.update(modelEntity, session, previousModel)) {
            transaction.commit();
            return "Successfully Updated";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public String deleteModel(String model) {
        Transaction transaction = session.beginTransaction();
        if(modelDao.delete(model, session)) {
            transaction.commit();
            return "Successfully Deleted";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public ModelDto getModel(String model) {
        ModelEntity modelEntity = modelDao.get(model, session);
        return new ModelDto(
                modelEntity.getModel(),
                modelEntity.getCategoryEntity().getCategory(),
                modelEntity.getBrandEntity().getBrand(),
                modelEntity.getCreatedBy(),
                modelEntity.getUpdatedBy()
        );
    }
    @Override
    public List<ModelDto> getAllModels() {
        ArrayList<ModelDto> modelDtos = new ArrayList<>();
        List<ModelEntity> modelEntities = modelDao.getAll(session);

        for (ModelEntity modelEntity : modelEntities) {
            ModelDto modelDto = new ModelDto(
                    modelEntity.getModel(),
                    modelEntity.getCategoryEntity().getCategory(),
                    modelEntity.getBrandEntity().getBrand(),
                    modelEntity.getCreatedBy(),
                    modelEntity.getUpdatedBy()
            );
            modelDtos.add(modelDto);
        }
        return modelDtos;
    }
}
