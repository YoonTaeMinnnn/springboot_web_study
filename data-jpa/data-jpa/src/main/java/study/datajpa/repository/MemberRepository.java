package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

// @Repository 필요없음
public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByUserNameAndAgeGreaterThan(String name, int age);

    // 많이 사용안함..
    @Query(name = "Member.findByUserName")
    List<Member> findByUserName(@Param("userName") String username);


    // 어플리케이션 로딩 시점에 오타 잡아줌 ==> 장점
    @Query("select m from Member m where m.userName = :userName and m.age = :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);




}
