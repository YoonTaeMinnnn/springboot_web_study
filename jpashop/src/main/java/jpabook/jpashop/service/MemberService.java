package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)       //스프링이 제공하는 어노테이션 추천(디폴트로 readonly로 설정됨)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입 : 중복인증포함
    @Transactional
    public void join(Member member){
        validateDulidateMember(member);
        memberRepository.save(member);
    }

    //회원 전체 조회
    public List<Member> find(){
        return  memberRepository.findAll();
    }

    //개별 회원 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


    private void validateDulidateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
