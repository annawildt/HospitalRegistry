package com.company.People;

import com.company.Addresses.Address;

import java.util.List;

public abstract class Person {
    String name;
    List<Address> addresses;

    public String getName() {
        return name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
