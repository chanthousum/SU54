package com.setec.su54_pos_api.services;

import com.setec.su54_pos_api.models.MyUser;
import com.setec.su54_pos_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<MyUser> getUserAll() {
        var users = userRepository.findAll();
        return  users;
    }
}
