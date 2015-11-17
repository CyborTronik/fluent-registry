package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.UserRepository;
import org.jasypt.util.password.PasswordEncryptor;

import javax.inject.Inject;

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
    public void createUser(String displayName, String email, String password) {
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        userRepository.createUser(displayName, email, encryptedPassword);
    }

}
