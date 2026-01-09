package com.setec.su54_pos_api.services;
import java.util.List;

import com.setec.su54_pos_api.exceptions.MyResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.setec.su54_pos_api.models.User;
import com.setec.su54_pos_api.models.UserProfile;
import com.setec.su54_pos_api.repositorys.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    final private UserRepository _userRepository;

    public UserService(UserRepository userRepository) {
        super();
        this._userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        var users = this._userRepository.findAll();
        return users;
    }

    @Transactional
    @Async
    public void createUser(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail().toLowerCase());

        UserProfile userProfile = new UserProfile();
        userProfile.setAddress(user.getUserProfile().getAddress());
        userProfile.setPhone(user.getUserProfile().getPhone());

        userProfile.setUser(user);
        user.setUserProfile(userProfile);

        this._userRepository.save(user);
    }

    @Transactional
    @Async
    public User findUserById(int id) {
        var user = this._userRepository.findUserById(id);
        if (user == null) {
            throw new MyResourceNotFoundException("User not found with id " + id);
        }
        return user;
    }

    @Transactional
    @Async
    public List<User> findUserByName(String name) {
        var users = this._userRepository.findByUsernameContainingIgnoreCase(name);
        return users;
    }

    @Transactional
    @Async
    public void deleteUserById(int id) {
        this.findUserById(id); // Check if user exists
        this._userRepository.deleteById(id);
    }

    @Transactional
    @Async
    public void updateUserById(int id, User user) {
        var user1 = this.findUserById(id); // Check if user exists
        user1.setUsername(user.getUsername().toLowerCase());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail().toLowerCase());
        user1.getUserProfile().setAddress(user.getUserProfile().getAddress());
        user1.getUserProfile().setPhone(user.getUserProfile().getPhone());
        this._userRepository.save(user1);
    }

}
