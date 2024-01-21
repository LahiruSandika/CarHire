package service.custom.impl;

import dao.DaoFactory;
import dao.custom.CustomerDao;
import dto.CustomerDto;
import entity.CustomerEntity;
import entity.embedded.CustomerAddress;
import entity.embedded.CustomerName;
import org.hibernate.Transaction;
import service.custom.CustomerService;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);

    @Override
    public String saveCustomer(CustomerDto customerDto) {
        Transaction transaction = session.beginTransaction();
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(customerDto.getId());
        customerEntity.setTitle(customerDto.getTitle());
        customerEntity.setName(new CustomerName(customerDto.getFirstName(), customerDto.getLastName()));
        customerEntity.setNic(customerDto.getNic());
        customerEntity.setAddress(new CustomerAddress(customerDto.getAddress(), customerDto.getCity(), customerDto.getProvince()));
        customerEntity.setPhone(customerDto.getPhone());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setRenting(customerDto.getRenting());
        customerEntity.setCreatedBy(customerDto.getCreatedBy());

        if(customerDao.save(customerEntity, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public String updateCustomer(CustomerDto customerDto) {
        session.clear();
        Transaction transaction = session.beginTransaction();
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(customerDto.getId());
        customerEntity.setTitle(customerDto.getTitle());
        customerEntity.setName(new CustomerName(customerDto.getFirstName(), customerDto.getLastName()));
        customerEntity.setNic(customerDto.getNic());
        customerEntity.setAddress(new CustomerAddress(customerDto.getAddress(), customerDto.getCity(), customerDto.getProvince()));
        customerEntity.setPhone(customerDto.getPhone());
        customerEntity.setCreatedBy(customerDto.getCreatedBy());
        customerEntity.setUpdatedBy(customerDto.getUpdatedBy());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setRenting(customerDto.getRenting());
        System.out.println(customerEntity.toString());

        if(customerDao.update(customerEntity, session)) {
            transaction.commit();
            return "Successfully Updated";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public String deleteCustomer(String id) {
        Transaction transaction = session.beginTransaction();
        if(customerDao.delete(id, session)) {
            transaction.commit();
            return "Deleted Successfully";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public CustomerDto getCustomer(String id) {
        CustomerEntity customerEntity = customerDao.get(id, session);
        return customerEntity != null ? new CustomerDto(
                customerEntity.getId(),
                customerEntity.getTitle(),
                customerEntity.getName().getFirstName(),
                customerEntity.getName().getLastName(),
                customerEntity.getNic(),
                customerEntity.getAddress().getAddress(),
                customerEntity.getAddress().getCity(),
                customerEntity.getAddress().getProvince(),
                customerEntity.getPhone(),
                customerEntity.getEmail(),
                customerEntity.getRenting(),
                customerEntity.getCreatedBy(),
                customerEntity.getUpdatedBy()
        ) : null;
    }
    @Override
    public List<CustomerDto> getAllCustomers() {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        List<CustomerEntity> customerEntities = customerDao.getAll(session);

        for (CustomerEntity customerEntity : customerEntities) {
            CustomerDto customerDto = new CustomerDto(
                    customerEntity.getId(),
                    customerEntity.getTitle(),
                    customerEntity.getName().getFirstName(),
                    customerEntity.getName().getLastName(),
                    customerEntity.getNic(),
                    customerEntity.getAddress().getAddress(),
                    customerEntity.getAddress().getCity(),
                    customerEntity.getAddress().getProvince(),
                    customerEntity.getPhone(),
                    customerEntity.getEmail(),
                    customerEntity.getRenting(),
                    customerEntity.getCreatedBy(),
                    customerEntity.getUpdatedBy()
            );

            customerDtos.add(customerDto);
        }
        return customerDtos;
    }
}
