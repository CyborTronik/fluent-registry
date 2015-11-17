package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.bean.UserRequest;
import com.github.cybortronik.registry.exception.ValidationException;
import com.github.cybortronik.registry.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.jasypt.util.password.PasswordEncryptor;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Created by stanislav on 10/28/15.
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncryptor passwordEncryptor;

    @Inject
    public UserServiceImpl(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User findById(String uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public User find(Login login) {
        User user = userRepository.findByEmail(login.getEmail());
        if (user != null && passwordEncryptor.checkPassword(login.getPassword(), user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    @Override
    public UUID createUser(String displayName, String email, String password) {
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        return userRepository.createUser(displayName, email, encryptedPassword);
    }

    @Override
    public User createUser(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword()))
            throw new ValidationException("Passwords doesn't match");
        UUID uuid = createUser(userRequest.getDisplayName(), userRequest.getEmail(), userRequest.getPassword());
        return findById(uuid.toString());
    }

    @Override
    public void addRoleToUser(String role, String email) {
        throw new NotImplementedException("Not implemented role functionality");
    }

}
