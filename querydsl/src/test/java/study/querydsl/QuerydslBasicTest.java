package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.*;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//static import
import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    //필드로 빼서 사용 가능
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void before() {
        jpaQueryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);


        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJpql() {
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    public void startQuerydsl() {

        Member findMember = jpaQueryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");

    }


    @Test
    public void search() {
        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    // and 방식
    @Test
    public void searchAndParam() {
        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        , member.age.eq(10))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    public void resultFetch() {
//        List<Member> members = jpaQueryFactory
//                .selectFrom(member)
//                .fetch();
//
//        Member member = jpaQueryFactory
//                .selectFrom(QMember.member)
//                .fetchOne();
//
//        Member firstMember = jpaQueryFactory
//                .selectFrom(QMember.member)
//                .fetchFirst();


        // paging 쿼리 (total count 쿼리 추가 실행)
        QueryResults<Member> results = jpaQueryFactory
                .selectFrom(QMember.member)
                .fetchResults();

        // select -> count (id로 카운트)
        long count = jpaQueryFactory
                .selectFrom(member)
                .fetchCount();

        System.out.println("count = " + count);

        results.getTotal();
        List<Member> content = results.getResults();

        System.out.println("results.getTotal() = " + results.getTotal());
        System.out.println("content = " + content);

    }


    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순
     * 2. 회원 이름 올림차순
     * 단, 회원 이름이 없으면, 마지막에 출력(nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();


        Member member5 = members.get(0);
        Member member6 = members.get(1);
        Member memberNull = members.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();


    }

    @Test
    public void paging1() {
        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)   //인덱스 1 부터
                .limit(2)  //2개까지
                .fetch();

        assertThat(members.size()).isEqualTo(2);
    }

    // 집합
    @Test
    public void aggregation() {
        List<Tuple> result = jpaQueryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구하라
     */
    @Test
    @DisplayName("간단 테스트")
    public void group() {
        List<Tuple> result = jpaQueryFactory
                .select(
                        team.name,
                        member.age.avg()
                )
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    /**
     * 팀 A에 소속된 모든 회원 (조인)
     * join : inner join
     */
    @Test
    public void join() {
        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    // 연관관계 없는 조인 (세타 조인)
    @Test
    public void theta_Join(){
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Member> result = jpaQueryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 회원과 팀을 조인하면서, 팀이름이 teamA인 팀만 조인, 회원은 모두 조회
     * jpql : select m,t from Member m left outer join m.team t on t.name = 'teamA'
     */
    @Test
    public void join_on_filtering() {
        List<Tuple> result = jpaQueryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * 연관관계가 없는 엔티티 외부조인
     * pk - fk 로 조인x | 이름 = 이름 으로 조인
     * 회원의 이름과 팀 이름이 같은 대상 외부 조인
     */
    @Test
    public void join_on_relation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));


        List<Tuple> result = jpaQueryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

    }

    // 페치 조인 적용 x
    @Test
    public void fetchJoinNo() {
        em.flush();
        em.clear();

        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

    }


    //페치 조인 적용
    @Test
    public void fetchJoinUse() {
        em.flush();
        em.clear();

        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();
    }

    /**
     * 나이가 가장 많은 회원 조회
     * 서브쿼리 사용
     *
     */
    @Test
    public void subQuery() {
        QMember memberSub = new QMember("memberSub");

        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                )).fetchOne();

        assertThat(findMember.getAge()).isEqualTo(40);
    }

    /**
     * 나이가 평균 이상인 멤버들
     * 서브 쿼리
     */
    @Test
    public void subQueryGoe() {
        QMember memberSub = new QMember("subQuery");
        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();
        assertThat(members).extracting("age")
                .containsExactly(30, 40);

    }

    @Test
    public void subQueryIn() {
        QMember memberSub = new QMember("subQuery");

        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                )).fetch();

        assertThat(members).extracting("age")
                .containsExactly(20, 30, 40);
    }

    // JPAExpression.select <- static import 가능
    // from 절에서의 서브 쿼리 불가능(하이버네이트 지원x)
    // 해결방안 : 서브쿼리 -> 조인 변경
    @Test
    public void selectSubQuery() {
        QMember memberSub = new QMember("subQuery");

        List<Tuple> members = jpaQueryFactory
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub)
                )
                .from(member)
                .fetch();

        for (Tuple tuple : members) {
            System.out.println("tuple = " + tuple);
        }
    }

    // case - 사용안하는것 권장
    // db 본연의 목적 - 데이터 조회
    @Test
    public void basicCase() {
        List<String> results = jpaQueryFactory
                .select(member.age.when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();
        for (String result : results) {
            System.out.println("result = " + result);
        }
    }

    // 문자 or 상수 더하기
    @Test
    public void constant() {
        List<Tuple> result = jpaQueryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    // 프로젝션 대상 하나 인경우
    @Test
    public void simpleProjection() {
        List<String> usernames = jpaQueryFactory
                .select(member.username)
                .from(member)
                .fetch();

        List<Member> members = jpaQueryFactory
                .selectFrom(member)
                .fetch();

        for (String username : usernames) {
            System.out.println("username = " + username);
        }

        for (Member member1 : members) {
            System.out.println("member1 = " + member1);
        }
    }

    // 투플의 경우, 레파지토리 계층에서만 사용 권장
    // dto로 변환 후 사용권장
    @Test
    public void tupleProjection() {
        List<Tuple> tuples = jpaQueryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : tuples) {
            System.out.println("tuple.get(member.username) = " + tuple.get(member.username));
            System.out.println("tuple.get(member.age) = " + tuple.get(member.age));
        }
    }

    // jpa dto 조회
    @Test
    public void findDto() {
        List<MemberDto> memberDtos = em.createQuery("select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }


    // dto => 기본생성자 존재해야함
    // setter 주입
    // getter, setter 존재해야함
    @Test
    public void findDtoByQuerydsl() {
        List<MemberDto> memberDtos = jpaQueryFactory
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // 필드 입 : getter, setter 없어도됨
    @Test
    public void findDtoByFields() {
        List<MemberDto> memberDtos = jpaQueryFactory
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    //생성자 주입
    @Test
    public void findDtoByConstructor() {
        List<MemberDto> memberDtos = jpaQueryFactory
                .select(Projections.constructor(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // 필드주입 - 필드이름 = 엔티티이름 같아야함
    // 다를 경우, as 사용
    @Test
    public void findDtoByUserDto() {
        QMember memberSub = new QMember("memberSub");

        List<UserDto> memberDtos = jpaQueryFactory
                .select(Projections.fields(UserDto.class, member.username.as("name"),
                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub), "age"
                        )
                        )
                )
                .from(member)
                .fetch();
        for (UserDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // 생성자 주입 - 타입만 같으면 가능
    @Test
    public void findDtoByUserDtoByConstructor() {
        QMember memberSub = new QMember("memberSub");

        List<UserDto> memberDtos = jpaQueryFactory
                .select(Projections.constructor(UserDto.class, member.username,
                                JPAExpressions
                                        .select(memberSub.age.max())
                                        .from(memberSub)

                        )
                )
                .from(member)
                .fetch();
        for (UserDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }


    // 컴파일 에러 잡기 가능
    // dto -> querydsl에 대한 의존성이 생김 (순수한 dto라 볼수없음)
    // 아키텍쳐에 따라 유연하게 결정
    @Test
    public void findDtoByQueryProjection() {
        List<MemberDto> memberDtos = jpaQueryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
        LocalDateTime time = LocalDateTime.now();

        Optional<String> text = Optional.ofNullable("ads");

    }


    // 동적쿼리 - 이름,나이 존재하면 where 절에 조건넣음
    @Test
    public void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    //
    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();

        return result;
    }

    // where 절에 null 이면 무시
    // where 절 가독성 좋음
    //querydsl - 재사용성 가능 (자바코드이기 때문에)
    @Test
    public void dynamicQueryWhere() {
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null? member.username.eq(usernameCond) : null;
    }

    // 컴포지션 가능
    private Predicate allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    //벌크연산
    @Test
    public void bulkUpdate() {
        long count = jpaQueryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        em.clear();

        // 나이 1 빼기
//        long count2 = jpaQueryFactory
//                .update(member)
//                .set(member.age, member.age.add(-1))
//                .execute();


        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .fetch();
        for (Member member1 : result) {
            System.out.println("member1 = " + member1.getUsername());
        }

    }

    //벌크 삭제
    @Test
    public void deleteBulk() {
        long count = jpaQueryFactory
                .delete(member)
                .where(member.age.lt(18))
                .execute();

    }

    //sql function : ex) replace 함수
    @Test
    public void sqlFunction() {
        List<String> result = jpaQueryFactory
                .select(
                        Expressions.stringTemplate("function('regexp_replace', {0}, {1}, {2})",
                                member.username,
                                "member",
                                "M")
                ).from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    // 소문자 함.
    @Test
    public void sqlFunction2() {
        List<String> result = jpaQueryFactory
                .select(member.username)
                .from(member)
//                .where(member.username.eq(Expressions.stringTemplate("function('lower', {0})", member.username)))
                .where(member.username.eq(member.username.lower()))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void practiceV1() {
        String username = "member1";
        Integer age = null;

        //동적 쿼리
        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(allEq(username, age))
                .fetch();

    }

    private BooleanExpression usernameEqV2(String username) {
        return username != null? member.username.eq(username) : null;
    }

    private BooleanExpression ageEqV2(Integer age) {
        return age != null? member.age.eq(age) : null;
    }

    private Predicate allEqV2(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    public void practiceV2() {
        long updateCount = jpaQueryFactory
                .update(member)
                .set(member.username, "memberA")
                .where(member.username.eq("member1"))
                .execute();

        assertThat(updateCount).isEqualTo(1);

        long deleteCount = jpaQueryFactory
                .delete(member)
                .where(member.username.eq("member1"))
                .execute();

        assertThat(deleteCount).isEqualTo(1);

    }

    @Test
    public void orderPagingTest() {




    }


}
