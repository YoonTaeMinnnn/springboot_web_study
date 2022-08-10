package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;
import java.util.Optional;

// @Repository 필요없음
public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByUserNameAndAgeGreaterThan(String name, int age);

    // 실무에선 거의 사용 안함..
    @Query(name = "Member.findByUserName")
    List<Member> findByUserName(@Param("userName") String username);

    //파라미터 순서 동일하게
    List<Member> findByUserNameAndAge(String name, int age);

    // 어플리케이션 로딩 시점에 오타 잡아줌 ==> 장점
    @Query("select m from Member m where m.userName = :userName and m.age = :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);


    @Query("select m.userName from Member m")
    List<String> findUserNameList();

    //dto 조회
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.userName, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.age in :ages")
    List<Member> findByTeams(@Param("teams") List<Integer> ages);
}
