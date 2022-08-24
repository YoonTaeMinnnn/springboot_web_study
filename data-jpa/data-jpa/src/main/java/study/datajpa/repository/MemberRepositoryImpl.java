package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;


// 커스텀 레파지토리 사용
// ex) querydsl
// 구현클래스 이름 규칙 ==> MemberRepository + Impl (동작가능) ==> 스프링데이터jpa 가 스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;


    @Override
    public List<Member> findMember() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
