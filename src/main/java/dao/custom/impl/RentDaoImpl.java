package dao.custom.impl;

import dao.custom.RentDao;
import entity.RentEntity;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RentDaoImpl implements RentDao {
    @Override
    public boolean save(RentEntity rentEntity, Session session) {
        try {
            String id = (String) session.save(rentEntity);
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
    public boolean update(RentEntity rentEntity, Session session) {
        try {
            session.update(rentEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean update(RentEntity rentEntity, Session session, String previousId) {
        return false;
    }

    @Override
    public boolean delete(String s, Session session) {
        try {
            RentEntity rentEntity = session.get(RentEntity.class, s);
            session.delete(rentEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public RentEntity get(String s, Session session) {
        RentEntity rentEntity = session.get(RentEntity.class, s);
        return rentEntity;
    }

    @Override
    public List<RentEntity> getAll(Session session) {
        String hql = "FROM RentEntity";
        Query query = session.createQuery(hql);
        List<RentEntity> rentEntities = query.list();
        return rentEntities;
    }
}
