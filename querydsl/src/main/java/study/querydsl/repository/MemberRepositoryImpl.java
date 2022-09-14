package study.querydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.dto.MemberSearchDto;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;


public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchDto memberSearchDto) {
        return jpaQueryFactory
                .select(new QMemberTeamDto(member.id.as("memberId"), member.username, member.age
                        ,team.id.as("teamId"), team.name.as("teamName")
                ))
                .from(member)
                .join(member.team, team)
                .where(usernameEq(memberSearchDto.getUsername()),
                        teamNameEq(memberSearchDto.getTeamName()),
                        ageGoe(memberSearchDto.getAgeGoe()),
                        ageLoe(memberSearchDto.getAgeLoe())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchDto memberSearchDto, Pageable pageable) {
        QueryResults<MemberTeamDto> result = jpaQueryFactory
                .select(new QMemberTeamDto(member.id.as("memberId"), member.username, member.age
                        , team.id.as("teamId"), team.name.as("teamName")
                ))
                .from(member)
                .join(member.team, team)
                .where(usernameEq(memberSearchDto.getUsername()),
                        teamNameEq(memberSearchDto.getTeamName()),
                        ageGoe(memberSearchDto.getAgeGoe()),
                        ageLoe(memberSearchDto.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        long total = result.getTotal();
        List<MemberTeamDto> content = result.getResults();
        return new PageImpl<>(content , pageable, total);
    }

    // 카운트 쿼리 분리
    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchDto memberSearchDto, Pageable pageable) {
        List<MemberTeamDto> content = jpaQueryFactory
                .select(new QMemberTeamDto(member.id.as("memberId"), member.username, member.age
                        , team.id.as("teamId"), team.name.as("teamName")
                ))
                .from(member)
                .join(member.team, team)
                .where(usernameEq(memberSearchDto.getUsername()),
                        teamNameEq(memberSearchDto.getTeamName()),
                        ageGoe(memberSearchDto.getAgeGoe()),
                        ageLoe(memberSearchDto.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(member.count())
                .from(member)
                .leftJoin(member.team, team)  //조인이 필요없을 경우도 존재 - 성능 향상
                .where(
                        usernameEq(memberSearchDto.getUsername()),
                        teamNameEq(memberSearchDto.getTeamName()),
                        ageGoe(memberSearchDto.getAgeGoe()),
                        ageLoe(memberSearchDto.getAgeLoe())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
        // 페이지 사이즈보다 토탈이 작은경우 or 마지막 페이지인 경우 => 카운트 쿼리 생략 (카운트쿼리 최적화)

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
