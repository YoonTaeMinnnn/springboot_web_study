package jpabasic.ex1jellojpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("m")
public class Movie extends Item{

    private String director;

    private String actor;
}
