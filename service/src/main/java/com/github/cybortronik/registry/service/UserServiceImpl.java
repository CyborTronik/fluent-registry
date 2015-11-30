package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.bean.UserRequest;
import com.github.cybortronik.registry.exception.ValidationException;
import com.github.cybortronik.registry.repository.bean.FilterResult;
import com.github.cybortronik.registry.repository.bean.UserFilter;
import com.github.cybortronik.registry.repository.UserRepository;
import com.google.gson.JsonElement;
import org.jasypt.util.password.PasswordEncryptor;

import javax.inject.Inject;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
    public UUID createUser(String displayName, String email, String password, String details) {
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        return userRepository.createUser(displayName, email, encryptedPassword, details);
    }

    @Override
    public User createUser(UserRequest userRequest) {
        passwordsMustMatch(userRequest);
        JsonElement jsonElement = userRequest.getDetails();
        String details = jsonElement == null? null : jsonElement.toString();
        UUID uuid = createUser(userRequest.getDisplayName(), userRequest.getEmail(), userRequest.getPassword(), details);
        String userId = uuid.toString();
        if (userRequest.getRoles() != null)
            userRequest.getRoles().forEach(role -> userRepository.addUserRole(userId, role));
        return findById(userId);
    }

    private void passwordsMustMatch(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirmation()))
            throw new ValidationException("Passwords doesn't match");
    }

    @Override
    public void addRoleToUser(String role, String email) {
        User user = userRepository.findByEmail(email);
        userRepository.addUserRole(user.getId(), role);
    }

    @Override
    public User updateUser(String uuid, UserRequest request) {
        if (isNotBlank(request.getDisplayName()))
            userRepository.updateDisplayName(uuid, request.getDisplayName());
        if (isNotBlank(request.getEmail()))
            userRepository.updateEmail(uuid, request.getEmail());
        if (request.getRoles() != null)
            userRepository.setRoles(uuid, request.getRoles());
        if (isNotBlank(request.getPassword())) {
            passwordsMustMatch(request);
            String encryptedPassword = passwordEncryptor.encryptPassword(request.getPassword());
            userRepository.setPasswordHash(uuid, encryptedPassword);
        }
        if (request.getDetails() != null) {
            userRepository.updateDetails(uuid, request.getDetails());
        }
        return findById(uuid);
    }

    @Override
    public void delete(String uuid) {
        userRepository.disableById(uuid);
    }

    @Override
    public FilterResult<User> filter(UserFilter userFilter) {
        return userRepository.filter(userFilter);
    }

    @Override
    public void createUser(String displayName, String email, String password) {
        createUser(displayName, email, password, null);
    }

}
