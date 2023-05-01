package study.querydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.dto.MemberSearchDto;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

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


    @Override
    public Slice<MemberTeamDto> findMemberList(Pageable pageable) {
        List<MemberTeamDto> members = jpaQueryFactory.select(
                        new QMemberTeamDto(member.id.as("memberId"), member.username, member.age
                                , team.id.as("teamId"), team.name.as("teamName")
                        )
                )
                .from(member)
                .join(member.team, team)
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new)) //배열만 가능!!
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(members, pageable, nextPage(pageable, members));
    }

    public boolean nextPage(Pageable pageable, List<MemberTeamDto> members) {
        if (members.size() > pageable.getPageSize()){
            members.remove(pageable.getPageSize());
            return true;
        } else {
            return false;
        }
    }

    public List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(
                s -> {
                    Order order = s.isAscending() ? Order.ASC : Order.DESC;
                    String property = s.getProperty();
                    PathBuilder orderByExpression = new PathBuilder(member.getType(), member.getMetadata());
                    orders.add(new OrderSpecifier(order, orderByExpression.get(property)));
                }
        );

        return orders;
    }
}
