package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchDto;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

// 2가지 방법 : jpaQueryFactory 를 빈으로 등록 후 인젝션
//    public MemberJpaRepository(EntityManager em) {
//        this.em = em;
//        this.jpaQueryFactory = new JPAQueryFactory(em);
//    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findAll_querydsl() {
        return jpaQueryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_querydsl(String username) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchDto memberSearchDto) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // 문자열 null 검증 시, StringUtils.hasText 권장 -> "" 경우도 있어서
        if (hasText(memberSearchDto.getUsername())){
            booleanBuilder.and(member.username.eq(memberSearchDto.getUsername()));
        }
        if (hasText(memberSearchDto.getTeamName())) {
            booleanBuilder.and(team.name.eq(memberSearchDto.getTeamName()));
        }
        if (memberSearchDto.getAgeGoe() != null) {
            booleanBuilder.and(member.age.goe(memberSearchDto.getAgeGoe()));
        }
        if (memberSearchDto.getAgeLoe() != null) {
            booleanBuilder.and(member.age.loe(memberSearchDto.getAgeLoe()));
        }

        return jpaQueryFactory
                .select(new QMemberTeamDto(member.id.as("memberId"), member.username, member.age
                    ,team.id.as("teamId"), team.name.as("teamName")
                ))
                .from(member)
                .where(booleanBuilder)
                .join(member.team, team)
                .fetch();

    }

    public List<MemberTeamDto> search(MemberSearchDto memberSearchDto) {
        return jpaQueryFactory
                .select(new QMemberTeamDto(member.id.as("memberId"), member.username, member.age
                        ,team.id.as("teamId"), team.name.as("teamName")
                ))
                .from(member)
                .where(usernameEq(memberSearchDto.getUsername()),
                        teamNameEq(memberSearchDto.getTeamName()),
                        ageGoe(memberSearchDto.getAgeGoe()),
                        ageLoe(memberSearchDto.getAgeLoe())
                )
                .join(member.team, team)
                .fetch();
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }



}
