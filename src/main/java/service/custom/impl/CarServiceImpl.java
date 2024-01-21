package service.custom.impl;

import dao.DaoFactory;
import dao.custom.BrandDao;
import dao.custom.CarDao;
import dao.custom.CategoryDao;
import dao.custom.ModelDao;
import dto.CarDto;
import entity.CarEntity;
import org.hibernate.Transaction;
import service.custom.CarService;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    CarDao carDao = (CarDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR);
    CategoryDao categoryDao = (CategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CATEGORY);
    BrandDao brandDao = (BrandDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BRAND);
    ModelDao modelDao = (ModelDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.MODEL);

    @Override
    public String saveCar(CarDto carDto) {
        Transaction transaction = session.beginTransaction();
        CarEntity carEntity = new CarEntity();

        carEntity.setId(carDto.getId());
        carEntity.setVehicleNumber(carDto.getVehicleNumber());
        carEntity.setCategoryEntity(categoryDao.get(carDto.getCategory(), session));
        carEntity.setBrandEntity(brandDao.get(carDto.getBrand(), session));
        carEntity.setModelEntity(modelDao.get(carDto.getModel(), session));
        carEntity.setYear(carDto.getYear());
        carEntity.setDailyRental(carDto.getDailyRental());
        carEntity.setAvailability(carDto.getAvailability());
        carEntity.setCreatedBy(carDto.getCreatedBy());
        System.out.println("From Service: " + carEntity.toString());

        if(carDao.save(carEntity, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public String updateCar(CarDto carDto) {
        session.clear();
        Transaction transaction = session.beginTransaction();
        CarEntity carEntity = new CarEntity();

        carEntity.setId(carDto.getId());
        carEntity.setVehicleNumber(carDto.getVehicleNumber());
        carEntity.setCategoryEntity(categoryDao.get(carDto.getCategory(), session));
        carEntity.setBrandEntity(brandDao.get(carDto.getBrand(), session));
        carEntity.setModelEntity(modelDao.get(carDto.getModel(), session));
        carEntity.setYear(carDto.getYear());
        carEntity.setDailyRental(carDto.getDailyRental());
        carEntity.setAvailability(carDto.getAvailability());
        carEntity.setCreatedBy(carDto.getCreatedBy());
        carEntity.setUpdatedBy(carDto.getUpdatedBy());
        System.out.println("From Service: " + carEntity.toString());

        if(carDao.update(carEntity, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public String deleteCar(String id) {
        Transaction transaction = session.beginTransaction();
        if(carDao.delete(id, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public CarDto getCar(String id) {
        CarEntity carEntity = carDao.get(id, session);
        return carEntity != null ? new CarDto(
                carEntity.getId(),
                carEntity.getVehicleNumber(),
                carEntity.getCategoryEntity().getCategory(),
                carEntity.getBrandEntity().getBrand(),
                carEntity.getModelEntity().getModel(),
                carEntity.getYear(),
                carEntity.getDailyRental(),
                carEntity.getAvailability(),
                carEntity.getCreatedBy(),
                carEntity.getUpdatedBy()
        ) : null;
    }

    @Override
    public List<CarDto> getAllCars() {
        ArrayList<CarDto> carDtos = new ArrayList<>();
        List<CarEntity> carEntities = carDao.getAll(session);

        for(CarEntity carEntity : carEntities) {
            CarDto carDto = new CarDto(
                    carEntity.getId(),
                    carEntity.getVehicleNumber(),
                    carEntity.getCategoryEntity().getCategory(),
                    carEntity.getBrandEntity().getBrand(),
                    carEntity.getModelEntity().getModel(),
                    carEntity.getYear(),
                    carEntity.getDailyRental(),
                    carEntity.getAvailability(),
                    carEntity.getCreatedBy(),
                    carEntity.getUpdatedBy()
            );
            carDtos.add(carDto);
        }
        return carDtos;
    }
}
