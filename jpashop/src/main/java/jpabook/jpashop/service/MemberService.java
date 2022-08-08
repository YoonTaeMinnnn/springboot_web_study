package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)       //스프링이 제공하는 어노테이션 추천(디폴트로 readonly로 설정됨) => 읽기전용(조회의 경우)
public class MemberService {

    private final MemberRepositoryOld memberRepository;

    //회원가입 : 중복인증포함
    @Transactional          //jpa내의 데이터 변경 (추가,변경,삭제) => 읽기전용x
    public Long join(Member member){
        validateDulidateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //회원 전체 조회
    public List<Member> find(){
        return  memberRepository.findAll();
    }

    //개별 회원 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


    private void validateDulidateMember(Member member) {  //같은 이름 존재 하면 예외처리
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void updateMember(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
