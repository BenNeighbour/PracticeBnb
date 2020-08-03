package com.benneighbour.practicebnb.propertyServer.dao;

import com.benneighbour.practicebnb.propertyServer.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyDao extends JpaRepository<Property, UUID> {

  Property findPropertyByName(String name);

  Property findPropertyById(UUID id);
}
