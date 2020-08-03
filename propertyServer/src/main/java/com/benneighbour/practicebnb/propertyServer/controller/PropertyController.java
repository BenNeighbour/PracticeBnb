package com.benneighbour.practicebnb.propertyServer.controller;

import com.benneighbour.practicebnb.propertyServer.dao.PropertyDao;
import com.benneighbour.practicebnb.propertyServer.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/property")
public class PropertyController {

  @Autowired private PropertyDao dao;

  @GetMapping("/by/{id}")
  public Optional<Property> findPropertyById(@RequestParam("id") UUID id) {
    return Optional.of(dao.findPropertyById(id));
  }

  @GetMapping("/all/by/{id}")
  public Optional<List<Property>> findPropertiesByUserId(@PathVariable("id") UUID id) {
    return Optional.of(
        dao.findAll().stream()
            .filter(property -> property.getUserId().equals(id))
            .collect(Collectors.toList()));
  }
}
