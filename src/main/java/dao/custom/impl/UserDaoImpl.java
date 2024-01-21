package dao.custom.impl;

import dao.custom.UserDao;
import entity.UserEntity;
import org.hibernate.Session;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity userEntity, Session session) {
        return false;
    }

    @Override
    public boolean update(UserEntity userEntity, Session session) {
        return false;
    }

    @Override
    public boolean update(UserEntity userEntity, Session session, String previousId) {
        return false;
    }

    @Override
    public boolean delete(String s, Session session) {
        return false;
    }

    @Override
    public UserEntity get(String s, Session session) {
        UserEntity userEntity = session.get(UserEntity.class, s);
        return userEntity;
    }

    @Override
    public List<UserEntity> getAll(Session session) {
        return null;
    }
}
