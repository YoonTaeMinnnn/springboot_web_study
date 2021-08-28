package hello.core.member;

public class MemberApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());
        System.out.println("findMember = "+ findMember.getName());
        System.out.println("member = "+member.getName());

    }
}