# springboot_web_study
springboot study (basic ~ jpa, spring data jpa)


 

## * Junit
### assertJ
    assertions.assertThat(a).isSameAs(b)  : a와 b의 주소값 비교 (싱글톤 패턴 테스트 시)  
    assertions.assertThat(a).isNotSameAs(b)  
    assertions.asserThat(a).isInstanceOf(b.class)   : a 객체가 b 클래스의 인스턴스 인지 확인
    assertions.assertThat(a).isEqualTo(b)  : a와 b의 단순 값 비교

### jupiter
     Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()-> ac.getBean(MemberService.class) );

## * jackson (json parsing lib) spring boot 기
     
