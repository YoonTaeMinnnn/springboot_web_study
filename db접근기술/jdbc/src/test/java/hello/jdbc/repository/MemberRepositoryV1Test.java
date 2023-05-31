package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.net.URL;
import java.sql.SQLException;


@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void beforeEach() {
        // driverManager -> 항상 새로운 커넥션 사용
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);

        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);
        Member savedMember = memberRepositoryV1.save(member);
        log.info("member = {}", savedMember);

    }

}