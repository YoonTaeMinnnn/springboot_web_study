package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;  //spring-data-jpa 가 스스로 구현객체(프록시객체)를 주입
    @Autowired private TeamRepository teamRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());

        assertThat(saveMember.getId()).isEqualTo(findMember.get().getId());
        assertThat(saveMember.getUserName()).isEqualTo(findMember.get().getUserName());
        assertThat(saveMember).isEqualTo(findMember.get());
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen() {

        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("aaa", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUserNameAndAgeGreaterThan("aaa", 10);

        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.get(0).getUserName()).isEqualTo("aaa");

    }

    @Test
    public void testQuery() {
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("aaa", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUserNameAndAge("aaa", 10);
        assertThat(members.get(0)).isEqualTo(member1);
    }

    @Test
    public void findUserNameList() {
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> userNames = memberRepository.findUserNameList();
        assertThat(userNames.size()).isEqualTo(2);
    }

    @Test
    public void findMemberDto() {

        Team team1 = new Team("teamA");
        teamRepository.save(team1);

        Member member1 = new Member("aaa", 10, team1);
        memberRepository.save(member1);

        List<MemberDto> memberDtos = memberRepository.findMemberDto();

        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }


    }

    @Test
    public void returnType() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> member = memberRepository.findListByUserName("AAA");

    }


    // List, Page, Slice 셋 중에 선택 가능
    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));


        int age = 10;

        // Page : 몇번째 페이지인지(0 페이지부터) , size : 크기 , 카운트 쿼리 날림, 총 페이지 개수를 알 수 있음.
        // Slice : count + 1개 조회 : 1개 존재 시, 다음페이지 존재 / 총 페이지 개수 알 수 없지만, 다음페이지 존재 여부를 알 수 있음.
        // ex ) page:1, size=3 ==> 3개씩 페이징 하는데, 두번째 페이지 가져와라
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //추가 실무팁(dto 전환)
        Page<MemberDto> members = page.map(member -> new MemberDto(member.getId(), member.getUserName(), null));
        //members 그대로 api 반환 가능


        //then
        List<Member> content = page.getContent();
        long totalCount = page.getTotalElements();

        System.out.println("content = " + content);
        System.out.println("totalCount = " + totalCount);

        assertThat(content.size()).isEqualTo(3); // 멤버 갯수
        assertThat(totalCount).isEqualTo(5);   //총 카운트
        assertThat(page.getNumber()).isEqualTo(0);  // 몇번째 페이지 인지
        assertThat(page.isFirst()).isTrue();  // 첫번째 페이지 여부
        assertThat(page.hasNext()).isTrue();  // 다음 페이지 존재 여부


    }
}
