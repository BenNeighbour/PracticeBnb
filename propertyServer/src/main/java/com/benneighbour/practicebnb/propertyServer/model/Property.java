package com.benneighbour.practicebnb.propertyServer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.UUID;

/*
 * @created 02/08/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "property")
public class Property {

  @Id
  @GeneratedValue
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(columnDefinition = "uuid", updatable = false)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "user_ID")
  private UUID userId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
