package com.setec.su54_pos_api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setec.su54_pos_api.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(int id);

    List<User> findByUsernameContainingIgnoreCase(String name);

}
