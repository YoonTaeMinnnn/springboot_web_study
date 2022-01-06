# springboot_web_study
springboot study (basic ~ mvc ~ jpa, spring data jpa)  



## * Junit
### assertJ
    assertions.assertThat(a).isSameAs(b)  : a와 b의 주소값 비교 (싱글톤 패턴 테스트 시)  
    assertions.assertThat(a).isNotSameAs(b)  
    assertions.asserThat(a).isInstanceOf(b.class)   : a 객체가 b 클래스의 인스턴스 인지 확인
    assertions.assertThat(a).isEqualTo(b)  : a와 b의 비교 (값, 인스턴스)

### jupiter
     Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()-> ac.getBean(MemberService.class) );





## * 정적팩토리메소드  vs 생성자 패턴
```sh
public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
```
- 생성자 패턴에 비해 가독성이 좋다. (생성자의 경우 이름을 지정할 수 없습니다)
- 호출할때마다 새로운 객체를 생성할 필요가 없다.
- 하위 자료형 객체를 반환할 수 있다. 
  
```sh
//클래스 A의 하위 클래스 B,C 
public static A hello(){
  if ~
    return new B();
  else if ~
    return new C();
  return new A();
}
```
- Lombok 사용시 편하게 생성 가능
```sh
@RequiredArgsConstructor(staticName = "hello")
```
