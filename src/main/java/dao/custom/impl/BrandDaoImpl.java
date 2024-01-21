package dao.custom.impl;

import dao.custom.BrandDao;
import entity.BrandEntity;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BrandDaoImpl implements BrandDao {
    @Override
    public boolean save(BrandEntity BrandEntity, Session session) {
        try {
            String brand = (String) session.save(BrandEntity);
            if(brand != null) {
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
    public boolean update(BrandEntity brandEntity, Session session) {
        try {
            session.update(brandEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean update(BrandEntity brandEntity, Session session, String previousId) {
        try {
            String hql = "UPDATE BrandEntity b SET b.brand = :brand, b.createdBy = :createdBy, b.updatedBy = :updatedBy WHERE b.brand = :previousId";
            Query query = session.createQuery(hql);
            query.setParameter("brand", brandEntity.getBrand());
            query.setParameter("createdBy", brandEntity.getCreatedBy());
            query.setParameter("updatedBy", brandEntity.getUpdatedBy());
            query.setParameter("previousId", previousId);
            return query.executeUpdate() > 0;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean delete(String s, Session session) {
        try {
            BrandEntity brandEntity = session.get(BrandEntity.class, s);
            session.delete(brandEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public BrandEntity get(String s, Session session) {
        BrandEntity brandEntity = session.get(BrandEntity.class, s);
        return brandEntity;
    }

    @Override
    public List<BrandEntity> getAll(Session session) {
        String hql = "FROM BrandEntity";
        Query query = session.createQuery(hql);
        List<BrandEntity> brandEntities = query.list();
        return brandEntities;
    }
}
