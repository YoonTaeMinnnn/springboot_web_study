package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item{

    private String author;

    private String isbn;

    public void updateBook(String name, int price, int stockQuantity, String author, String isbn) {
        updateItem(name, price, stockQuantity);
        this.author=author;
        this.isbn=isbn;
    }


}
