package dao.custom;

import dao.CrudDao;
import entity.BrandEntity;
import org.hibernate.Session;

import java.util.List;

public interface BrandDao extends CrudDao<BrandEntity, String> {
}
