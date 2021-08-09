package hello.hellospring.domain;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import javax.persistence.metamodel.IdentifiableType;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  //pk키 자동생성(db가 알아서 생성해주는것)
    private Long id;


    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
