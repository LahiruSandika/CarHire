package dao.custom.impl;

import dao.custom.CarDao;
import entity.CarEntity;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public boolean save(CarEntity carEntity, Session session) {
        try {
            String id = (String) session.save(carEntity);
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
    public boolean update(CarEntity carEntity, Session session) {
        try {
            session.update(carEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean update(CarEntity carEntity, Session session, String previousId) {
        return false;
    }

    @Override
    public boolean delete(String s, Session session) {
        try {
            CarEntity carEntity = session.get(CarEntity.class, s);
            session.delete(carEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public CarEntity get(String s, Session session) {
        CarEntity carEntity = session.get(CarEntity.class, s);
        return carEntity;
    }

    @Override
    public List<CarEntity> getAll(Session session) {
        String hql = "FROM CarEntity";
        Query query = session.createQuery(hql);
        List<CarEntity> carEntities = query.list();
        return carEntities;
    }
}
