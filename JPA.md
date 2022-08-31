# 정리 (강의 들으면서 새로 알게된 점)

## cascade , 고아 객체  

### cascade(영속성 전이) , orphanremoval = true (고아 객체)  
 - cascade : entity 의 상태변화를 연관된 entity로 전이
 - orphanremoval = true : parent와 연관이 끊어진 child 삭제
 
### casecadeType.REMOVE vs orphanremoval = true
 - parent 삭제 시, 연관된 child 모두 삭제 (공통) (delete 쿼리)
 - orphanremoval = true : parent의 컬렉션에서 특정 child 삭제 시, 해당 child 삭제 (delete 쿼리)

### casecadeType.ALL + orphanremoval = true
 - parent의 life cycle 에 완전히 종속적

## 성능최적화( n+1 최적화 )
 - lazy + fetch join (객체 그래프 루프를 돌아서 모두 영속화)
 - lazy + join문 x + batch size ( 1:n 에서의 fetch join 은 페이징 불가 -> join 문 삭제 후, batch size 로 in query 날려서 조회쿼리 하나만)
 - + 1:n 에서의 페이징 -> n:1 로 조회 후 페이징 가능
 - 둘 이상의 컬렉션은 fetch join 불가 ex) from Team t join fetch t.members join fetch t.orders


## spring-data-jpa  
 - @Transactional(readonly=true) : commit 시점에 flush 작동x (성능상 우위)
 - query method : 메소드 네임 규칙으로 조회쿼리 생성
 - @Query : 어플리케이션 로딩 시점에 에러 확인 가능 (엄청난 장점, named query와 비슷, but named query는 잘 사용x)
 - ex) @EntityGraph(attributePaths = {"team", "team.director"})  : fetch join (default : left join fetch)
 - @Modifying(clearAutomatically : true )  : executeUpdate() 실행 (update된 count수 반환) + em.clear()
 - Page, Slice, List : 페이징 조회 반환타입 3가지
 - Page의 경우 total query -> 성능이슈... countQuery를 통해 불필요한 조인쿼리 없애기 가능 /  Slice의 경우 countQuery x
 - Page객체 그대로 api 반환 가능 
 - *SimpleJpaRepository* (spring-data-jpa 구현체): save, delete 등 (트랜젝션처리) / save, delete -> @Transactional | default : readonly transactional
 - (dirty checking 외의 등록 삭제 조회 과정은 @Transactional 불필요 (이미 구현체에 달려있음)
 
 - id(pk)값이 존재하는 상태에서 save()호출 시, *persist* 대신 *merge*가 실행됨!!(주의) 
 - merge : db에 엔티티가 존재한다는 가정 하에 동작 (db에서 조회 후 모든 필드값을 업데이트 - 사용 비추천)
 - generatedValue 전략 사용x --> persistable인터페이스 구현해서 id존재여부 검증 절차 필요 (검증 여부에 따라 로직 변경)

## querydsl
 - JPAQueryFactory : (동시성 문제 해결) 다른 트랜젝션내에서는 동일한 entitymanager -> 각각 다른 영속성 컨텍스트 호출
