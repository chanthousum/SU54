package com.setec.su54_pos_api.repositories;

import com.setec.su54_pos_api.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
}
