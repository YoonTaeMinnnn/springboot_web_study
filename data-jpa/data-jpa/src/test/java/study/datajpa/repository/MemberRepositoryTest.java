package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;  //spring-data-jpa 가 스스로 구현객체(프록시객체)를 주입

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());

        assertThat(saveMember.getId()).isEqualTo(findMember.get().getId());
        assertThat(saveMember.getUserName()).isEqualTo(findMember.get().getUserName());
        assertThat(saveMember).isEqualTo(findMember.get());
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen() {

        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("aaa", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUserNameAndAgeGreaterThan("aaa", 10);

        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.get(0).getUserName()).isEqualTo("aaa");

    }

    @Test
    public void testQuery() {
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("aaa", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);


        List<Member> members = memberRepository.findUser("aaa", 10);
        assertThat(members.get(0)).isEqualTo(member1);


    }
}
