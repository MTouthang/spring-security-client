package mang.io.springsecurity.service;

import mang.io.springsecurity.entity.User;
import mang.io.springsecurity.entity.VerificationToken;
import mang.io.springsecurity.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
