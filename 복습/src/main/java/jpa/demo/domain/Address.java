package jpa.demo.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;

    private String zipcode;
}
