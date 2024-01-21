package service.custom;

import dto.UserDto;
import service.SuperService;

public interface UserService extends SuperService {
    UserDto getUser(String username);
}
