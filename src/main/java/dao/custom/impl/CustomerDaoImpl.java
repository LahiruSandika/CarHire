package dao.custom.impl;

import dao.custom.CustomerDao;
import entity.CustomerEntity;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customerEntity, Session session) {
        try {
            String id = (String) session.save(customerEntity);
            if(id != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean update(CustomerEntity customerEntity, Session session) {
        try {
            session.update(customerEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }

    }

    @Override
    public boolean update(CustomerEntity customerEntity, Session session, String previousId) {
        return false;
    }

    @Override
    public boolean delete(String s, Session session) {
        try {
            CustomerEntity customerEntity = session.get(CustomerEntity.class, s);
            session.delete(customerEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public CustomerEntity get(String s, Session session) {
        CustomerEntity customerEntity = (CustomerEntity) session.get(CustomerEntity.class, s);
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> getAll(Session session) {
        String hql = "FROM CustomerEntity";
        Query query = session.createQuery(hql);
        List<CustomerEntity> customerEntities = query.list();
        return customerEntities;
    }
}

