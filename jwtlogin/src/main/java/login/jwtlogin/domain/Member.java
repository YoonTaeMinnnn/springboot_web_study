package login.jwtlogin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String loginId;

    private String password;

    private String roles;

    private String email;

    private String university;

    private String dept;

    private Long reliability;

    @ElementCollection
    @CollectionTable(name = "friend" , joinColumns =
        @JoinColumn(name = "member_id")
    )
    private Set<Long> friends = new HashSet<>();


    @ElementCollection
    @CollectionTable(name = "favorite_food" , joinColumns =
    @JoinColumn(name = "member_id")
    )
    private List<String> favoriteFoods = new ArrayList<>();


    public Member(String name, String loginId, String password, String roles, String email, String university, String dept, Long reliability) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.roles= roles;
        this.email = email;
        this.university = university;
        this.dept = dept;
        this.reliability = reliability;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }else{
            return new ArrayList<>();
        }
    }



}
