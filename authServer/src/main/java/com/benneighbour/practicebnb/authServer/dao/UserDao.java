package com.benneighbour.practicebnb.authServer.dao;

import com.benneighbour.practicebnb.authServer.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {

    User findUserByUsername(String username);

    User findUserById(UUID id);

}
