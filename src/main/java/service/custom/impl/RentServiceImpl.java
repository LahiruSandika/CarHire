package service.custom.impl;

import dao.DaoFactory;
import dao.custom.CarDao;
import dao.custom.CustomerDao;
import dao.custom.RentDao;
import dto.RentDto;
import entity.CarEntity;
import entity.CustomerEntity;
import entity.RentEntity;
import org.hibernate.Transaction;
import service.custom.RentService;

import java.util.ArrayList;
import java.util.List;

public class RentServiceImpl implements RentService {
    RentDao rentDao = (RentDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.RENT);
    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
    CarDao carDao = (CarDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR);

    @Override
    public String saveRent(RentDto rentDto) {
        Transaction transaction = session.beginTransaction();
        RentEntity rentEntity = new RentEntity();
        CarEntity carEntity = carDao.get(rentDto.getCarId(), session);
        CustomerEntity customerEntity = customerDao.get(rentDto.getCustomerId(), session);

        rentEntity.setId(rentDto.getRentId());
        rentEntity.setCustomerEntity(customerDao.get(rentDto.getCustomerId(), session));
        rentEntity.setCarEntity(carDao.get(rentDto.getCarId(), session));
        rentEntity.setFromDate(rentDto.getFromDate());
        rentEntity.setToDate(rentDto.getToDate());
        rentEntity.setDailyRental(rentDto.getDailyRental());
        rentEntity.setTotal(rentDto.getTotal());
        rentEntity.setRefundableDeposit(rentDto.getRefundableDeposit());
        rentEntity.setAdvancePayment(rentDto.getAdvancePayment());
        rentEntity.setIsReturned(rentDto.getIsReturned());
        rentEntity.setDaysOverdue(rentDto.getDaysOverdue());
        rentEntity.setBalance(rentDto.getBalance());
        rentEntity.setCreatedBy(rentDto.getCreatedBy());


        carEntity.setAvailability("Unavailable");
        carDao.update(carEntity, session);

        customerEntity.setRenting("Yes");
        customerDao.update(customerEntity, session);

        if(rentDao.save(rentEntity, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public String updateRent(RentDto rentDto) {
        session.clear();
        Transaction transaction = session.beginTransaction();
        RentEntity rentEntity = new RentEntity();
        CarEntity carEntity = carDao.get(rentDto.getCarId(), session);
        CustomerEntity customerEntity = customerDao.get(rentDto.getCustomerId(), session);

        rentEntity.setId(rentDto.getRentId());
        rentEntity.setCustomerEntity(customerDao.get(rentDto.getCustomerId(), session));
        rentEntity.setCarEntity(carDao.get(rentDto.getCarId(), session));
        rentEntity.setFromDate(rentDto.getFromDate());
        rentEntity.setToDate(rentDto.getToDate());
        rentEntity.setDailyRental(rentDto.getDailyRental());
        rentEntity.setTotal(rentDto.getTotal());
        rentEntity.setRefundableDeposit(rentDto.getRefundableDeposit());
        rentEntity.setAdvancePayment(rentDto.getAdvancePayment());
        rentEntity.setIsReturned(rentDto.getIsReturned());
        rentEntity.setReturnedOn(rentDto.getReturnedOn());
        rentEntity.setDaysOverdue(rentDto.getDaysOverdue());
        rentEntity.setBalance(rentDto.getBalance());
        rentEntity.setCreatedBy(rentDto.getCreatedBy());
        rentEntity.setUpdatedBy(rentDto.getUpdatedBy());

        if(rentDto.getReturnedOn() == null) {
            carEntity.setAvailability("Unavailable");
            customerEntity.setRenting("Yes");
        } else {
            carEntity.setAvailability("Available");
            customerEntity.setRenting("No");
        }

        if(rentDao.update(rentEntity, session)) {
            transaction.commit();
            return "Successfully Updated";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public String deleteRent(String id) {
        Transaction transaction = session.beginTransaction();
        if(rentDao.delete(id, session)) {
            transaction.commit();
            return "Deleted Successfully";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public RentDto getRent(String id) {
        RentEntity rentEntity = rentDao.get(id, session);
        return rentEntity != null ? new RentDto(
                rentEntity.getId(),
                rentEntity.getCustomerEntity().getId(),
                rentEntity.getCarEntity().getId(),
                rentEntity.getFromDate(),
                rentEntity.getToDate(),
                rentEntity.getDailyRental(),
                rentEntity.getTotal(),
                rentEntity.getRefundableDeposit(),
                rentEntity.getAdvancePayment(),
                rentEntity.getIsReturned(),
                rentEntity.getReturnedOn(),
                rentEntity.getDaysOverdue(),
                rentEntity.getBalance(),
                rentEntity.getCreatedBy(),
                rentEntity.getUpdatedBy()
        ) : null;
    }

    @Override
    public List<RentDto> getAllRents() {
        ArrayList<RentDto> rentDtos = new ArrayList<>();
        List<RentEntity> rentEntities = rentDao.getAll(session);

        for (RentEntity rentEntity : rentEntities) {
            RentDto rentDto = new RentDto(
                    rentEntity.getId(),
                    rentEntity.getCustomerEntity().getId(),
                    rentEntity.getCarEntity().getId(),
                    rentEntity.getFromDate(),
                    rentEntity.getToDate(),
                    rentEntity.getDailyRental(),
                    rentEntity.getTotal(),
                    rentEntity.getRefundableDeposit(),
                    rentEntity.getAdvancePayment(),
                    rentEntity.getIsReturned(),
                    rentEntity.getReturnedOn(),
                    rentEntity.getDaysOverdue(),
                    rentEntity.getBalance(),
                    rentEntity.getCreatedBy(),
                    rentEntity.getUpdatedBy()
            );

            rentDtos.add(rentDto);
        }
        return rentDtos;
    }
}
