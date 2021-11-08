# springboot_study
springboot study (basic ~ jpa, spring data jpa)


<Junit 유용한 메소드들>  

assertions.assertThat(a).isSameAs(b)  : a와 b의 주소값 비교 (싱글톤 패턴 테스트 시)  
assertions.assertThat(a).isNotSameAs(b)  
--------------------------------------------------------------------  
assertions.asserThat(a).isInstanceOf(b.class)   : a 객체가 b 클래스의 인스턴스 인지 확인
--------------------------------------------------------------------  
assertions.assertThat(a).isEqualTo(b)  : a와 b의 단순 값 비교
