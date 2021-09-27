package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable               //jpa의 내장타입
@Getter
public class Address {
    private String city;

    private String street;

    private String zipcode;
}
