package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired private MemberJpaRepository memberJpaRepository;

//    @Test
//    public void testMember() {
//        Member member = new Member("memberA",10,);
//        Member saveMember = memberJpaRepository.save(member);
//
//        Member findMember = memberJpaRepository.find(saveMember.getId());
//
//        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
//        assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
//    }

}