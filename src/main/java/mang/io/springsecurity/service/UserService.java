package mang.io.springsecurity.service;

import mang.io.springsecurity.entity.User;
import mang.io.springsecurity.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);
}
