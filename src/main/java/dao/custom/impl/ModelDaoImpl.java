package dao.custom.impl;

import dao.custom.ModelDao;
import entity.ModelEntity;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ModelDaoImpl implements ModelDao {
    @Override
    public boolean save(ModelEntity modelEntity, Session session) {
        try {
            String model = (String) session.save(modelEntity);
            if(model != null) {
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
    public boolean update(ModelEntity modelEntity, Session session) {
        try {
            session.update(modelEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean update(ModelEntity modelEntity, Session session, String previousId) {
        try {
            String hql = "UPDATE ModelEntity m SET m.model = :model, m.brandEntity = :brand, m.categoryEntity = :category, m.createdBy = :createdBy, m.updatedBy = :updatedBy WHERE m.model = :previousId";
            Query query = session.createQuery(hql);
            query.setParameter("model", modelEntity.getModel());
            query.setParameter("brand", modelEntity.getBrandEntity());
            query.setParameter("category", modelEntity.getCategoryEntity());
            query.setParameter("createdBy", modelEntity.getCreatedBy());
            query.setParameter("updatedBy", modelEntity.getUpdatedBy());
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
            ModelEntity modelEntity = session.get(ModelEntity.class, s);
            session.delete(modelEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public ModelEntity get(String s, Session session) {
        ModelEntity modelEntity = session.get(ModelEntity.class, s);
        return modelEntity;
    }

    @Override
    public List<ModelEntity> getAll(Session session) {
        String hql = "FROM ModelEntity";
        Query query = session.createQuery(hql);
        List<ModelEntity> modelEntities = query.list();
        return modelEntities;
    }
}
