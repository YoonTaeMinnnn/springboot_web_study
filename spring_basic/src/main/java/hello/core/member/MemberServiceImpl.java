package hello.core.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //추상화                                                   //구체화
    //추상화에도 의존하고 구체화에도 의존한다  ==> 문제! (DIP원칙이 지켜지지 않고 있음)

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
