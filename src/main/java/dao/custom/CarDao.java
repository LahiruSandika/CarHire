package dao.custom;

import dao.CrudDao;
import entity.CarEntity;
import org.hibernate.Session;

public interface CarDao extends CrudDao<CarEntity, String> {

}
