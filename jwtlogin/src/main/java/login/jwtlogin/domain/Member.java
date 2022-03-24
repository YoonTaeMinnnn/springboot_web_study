package login.jwtlogin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.datatransfer.Clipboard;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String loginId;

    private String password;

    private String sex;

    private String roles;

    private String email;

    private String university;

    private String dept;

    private Integer sno;

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


    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    //------------------------------------------------------------------------------------------------


    public Member(String nickname, String loginId, String password, String sex, String email, String roles, String university, String dept, Integer sno, Long reliability) {
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.roles= roles;
        this.university = university;
        this.dept = dept;
        this.sno = sno;
        this.reliability = reliability;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }else{
            return new ArrayList<>();
        }
    }

    public void updateGrade() {
        this.roles += ",ROLE_ADMIN";
    }



}
