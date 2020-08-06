package com.benneighbour.practicebnb.authServer.controller;

import com.benneighbour.practicebnb.authServer.common.client.GlobalDao;
import com.benneighbour.practicebnb.authServer.dao.UserDao;
import com.benneighbour.practicebnb.authServer.model.user.User;
import com.benneighbour.practicebnb.authServer.model.user.role.Role;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/login")
class UserController {
  @Autowired private UserDao userDao;

  @Autowired private PasswordEncoder encoder;

  @Autowired private GlobalDao globalDao;

  private MeterRegistry meter = new SimpleMeterRegistry();

  @PostMapping("/new")
  public User createUser(@Valid @RequestBody User user) {
    Role role = new Role();
    role.setRoleType("USER");

    user.setRole(new ArrayList<>());
    user.getRole().add(role);

    // Encrypt password
    user.setPassword(encoder.encode(user.getPassword()));

    return userDao.save(user);
  }

  @GetMapping("/get")
  public User getUser(@RequestParam("userId") UUID userId) throws Exception {
    return globalDao.getUserById(userId);
  }
}
