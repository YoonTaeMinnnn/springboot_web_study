package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void 회원가입(){
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberService.findOne(savedId));
    }

    @Test
    public void 중복회원예외(){
        //given
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setName("kim");
        memberB.setName("kim");

        //when
        memberService.join(memberA);
        //then
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(memberB));

    }

}