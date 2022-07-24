cascade(영속성 전이) , orphanremoval = true (고아 객체)  
 - cascade : entity 의 상태변화를 연관된 entity로 전이
 - orphanremoval = true : parent와 연관이 끊어진 child 삭제

성능최적화( n+1 최적화 )
 - lazy + fetch join (객체 그래프 루프를 돌아서 모두 영속화)
 - lazy + join문 x + batch size ( 1:n 에서의 fetch join 은 페이징 불가 -> join 문 삭제 후, batch size 로 in query 날려서 조회쿼리 하나만)
 - + 1:n 에서의 페이징 -> n:1 로 조회 후 페이징 가능
 - 둘 이상의 컬렉션은 fetch join 불가 ex) from Team t join fetch t.members join fetch t.orders


