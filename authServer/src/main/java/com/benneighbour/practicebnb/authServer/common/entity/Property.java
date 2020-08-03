package com.benneighbour.practicebnb.authServer.common.entity;

/*
 * @created 02/08/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class Property {

    private String name;

    private String address;


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
}
