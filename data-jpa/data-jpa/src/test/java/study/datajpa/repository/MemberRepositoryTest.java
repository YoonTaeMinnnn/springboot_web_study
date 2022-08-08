package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

//    @Test
//    public void testMember() {
//        Member member = new Member("memberA");
//        Member saveMember = memberRepository.save(member);
//        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
//
//        assertThat(saveMember.getId()).isEqualTo(findMember.get().getId());
//        assertThat(saveMember.getUsername()).isEqualTo(findMember.get().getUsername());
//        assertThat(saveMember).isEqualTo(findMember.get());
//
//    }
}
