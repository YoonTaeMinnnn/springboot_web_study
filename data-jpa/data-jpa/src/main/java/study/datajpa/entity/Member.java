package study.datajpa.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName","age"})
@NamedQuery(
        name="Member.findByUserName",
        query = "select m from Member m where m.userName = :userName"
)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    private int age;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Member(String username, int age, Team team) {
        this.userName = username;
        this.age= age;
        changeTeam(team);
    }

    public Member(String username) {
        this.userName = username;
    }

    public Member(String username, int age) {
        this.userName = username;
        this.age= age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
