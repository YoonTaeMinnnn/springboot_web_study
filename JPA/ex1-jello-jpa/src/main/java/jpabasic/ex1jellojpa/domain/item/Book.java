package jpabasic.ex1jellojpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("b")
public class Book extends Item{

    private String author;

    private int isbn;


}
