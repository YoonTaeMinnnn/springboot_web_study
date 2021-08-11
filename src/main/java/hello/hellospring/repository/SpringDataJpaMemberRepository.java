package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> , MemberRepository{  //스프링이 알아서 구현체를 만들어서 등록해줌
    @Override
    Optional<Member> findByName(String name);
}
