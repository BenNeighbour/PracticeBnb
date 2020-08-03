package com.benneighbour.practicebnb.authServer.common.client;

import com.benneighbour.practicebnb.authServer.common.entity.Property;
import com.benneighbour.practicebnb.authServer.dao.UserDao;
import com.benneighbour.practicebnb.authServer.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class GlobalDao {

  @Autowired private RestTemplate restTemplate;

  @Autowired private UserDao userDao;

  public User getUserById(UUID id) {
    User user = userDao.findUserById(id);

    user.setProperties(
        restTemplate.getForObject("http://localhost:8081/property/all/by/" + id, List.class));

    return user;
  }
}
