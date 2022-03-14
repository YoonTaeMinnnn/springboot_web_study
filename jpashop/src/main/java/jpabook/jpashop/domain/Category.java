package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",              //조인테이블 방식 (조인컬럼 방식과 또 다른 방식)
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")  //(셀프조인)
    private Category parent;

    @OneToMany(mappedBy = "parent")   //부모 매핑 (셀프조인)
    private List<Category> child = new ArrayList<>();

    //연관관계 메소드

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
