package dao.custom.impl;

import dao.custom.CategoryDao;
import entity.CategoryEntity;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public boolean save(CategoryEntity categoryEntity, Session session) {
        try {
            String category = (String) session.save(categoryEntity);
            if (category != null) {
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
    public boolean update(CategoryEntity categoryEntity, Session session) {
        try {
            session.update(categoryEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }
    @Override
    public boolean update(CategoryEntity categoryEntity, Session session, String previousId) {
        try {
            String hql = "UPDATE CategoryEntity c SET c.category = :category, c.createdBy = :createdBy, c.updatedBy = :updatedBy WHERE c.category = :previousId";
            Query query = session.createQuery(hql);
            query.setParameter("category", categoryEntity.getCategory());
            query.setParameter("createdBy", categoryEntity.getCreatedBy());
            query.setParameter("updatedBy", categoryEntity.getUpdatedBy());
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
            CategoryEntity categoryEntity = session.get(CategoryEntity.class, s);
            session.delete(categoryEntity);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public CategoryEntity get(String s, Session session) {
        CategoryEntity categoryEntity = session.get(CategoryEntity.class, s);
        return categoryEntity;
    }

    @Override
    public List<CategoryEntity> getAll(Session session) {
        String hql = "FROM CategoryEntity";
        Query query = session.createQuery(hql);
        List<CategoryEntity> categoryEntities = query.list();
        return categoryEntities;
    }
}
