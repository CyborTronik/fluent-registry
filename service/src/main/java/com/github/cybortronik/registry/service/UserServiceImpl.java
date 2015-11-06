package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.UserRepository;

import javax.inject.Inject;

/**
 * Created by stanislav on 10/28/15.
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return null;
    }

}
