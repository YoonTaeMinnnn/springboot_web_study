package jpabasic.ex1jellojpa.domain;

import javax.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "locker_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker", fetch = FetchType.LAZY)
    private Member member;
}
