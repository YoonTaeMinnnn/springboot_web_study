package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 예제를 위한 엔티티 (연관관계 무시..)
@Getter
@Entity
public class Director {

    @Id
    @GeneratedValue
    @Column(name = "director_id")
    private Long id;

    private String name;
}
