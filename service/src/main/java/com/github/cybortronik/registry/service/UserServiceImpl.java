package com.github.cybortronik.registry.service;

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
}
