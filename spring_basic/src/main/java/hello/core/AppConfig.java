package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {  //공연 기획자 => 배우들의 섭외를 여기서..

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());  //객체 생성 -> 생성자 파라미터로 Memory멤버리파지토리가 들어감.
    }

    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();        //역할
    }

    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();             //역할
        return new RateDiscountPolicy();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

}
