package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;

// @Repository 필요없음
public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByUsername(String username);

}
