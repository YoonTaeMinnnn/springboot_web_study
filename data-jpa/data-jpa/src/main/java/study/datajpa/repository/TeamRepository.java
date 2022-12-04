package study.datajpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.datajpa.entity.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // @EntityGraph 사용 시, distinct 키워드 없이도, 하이버네이트 내부적으로 중복을 제거해줌!!
    @EntityGraph(attributePaths = {"members"})
    @Query("select t from Team t")
    List<Team> findByMembers();


}
