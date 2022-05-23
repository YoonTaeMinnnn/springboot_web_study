package jpabasic.ex1jellojpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("a")
public class Album extends Item{

    private String artist;

}
