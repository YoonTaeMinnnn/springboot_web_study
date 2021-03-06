package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MemberServiceImpl implements MemberService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //추상화                                                   //구체화
    //추상화에도 의존하고 구체화에도 의존한다  ==> 문제! (DIP원칙이 지켜지지 않고 있음)

    private final MemberRepository memberRepository;   //dip, ocp지키고있음. 추상화에만 의존, 구현체 변경에 있어서 클라이언트 코드 불변

    @Autowired  //자동으로 스프링 빈을 찾아서 주입
    public MemberServiceImpl(MemberRepository memberRepository) {   //생성자 주입
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
