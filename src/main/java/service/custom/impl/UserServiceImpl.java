package service.custom.impl;

import dao.DaoFactory;
import dao.custom.UserDao;
import dto.UserDto;
import entity.UserEntity;
import service.custom.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    @Override
    public UserDto getUser(String username) {
        UserEntity userEntity = userDao.get(username, session);
        if(userEntity != null) {
            return new UserDto(
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getMobile()
            );
        } else {
            return null;
        }
    }
}
