package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save member = {}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

//    public Optional<Member> findByLoginId(String loginId){
//        List<Member> all = findAll();
//        for (Member member : all) {
//            if (member.getLoginId().equals(loginId)){
//                return Optional.of(member);
//            }
//        }
//        return Optional.empty();
//    }

    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream().filter(
                m -> m.getLoginId().equals(loginId)).findAny();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

}
