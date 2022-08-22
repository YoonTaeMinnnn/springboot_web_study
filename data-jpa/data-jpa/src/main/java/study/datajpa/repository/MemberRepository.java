package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    List<Member> findByTeams(@Param("ages") List<Integer> ages);


    // find 다음 List 부분은 마음대로 정해도됨
    List<Member> findListByUserName(String name);

    // 카운트 쿼리 자체는 성능상 무거움..
    // 조인쿼리가 있는 경우, 카운트 쿼리를 분리하는 것을 추천 (카운트 쿼리에서는 조인할 필요가 없기 때문에)
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
    Page<Member> findByAgeCountQuery(int age, Pageable pageable);

    // Page or Slice or List : return type
    Page<Member> findByAge(int age, Pageable pageable);


    @Modifying(clearAutomatically = true) // executeUpdate 실행됨 + em.clear()
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

}
