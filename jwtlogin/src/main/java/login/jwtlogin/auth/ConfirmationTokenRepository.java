package login.jwtlogin.auth;

import login.jwtlogin.domain.email.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmationTokenRepository {

    private final EntityManager em;


    @Transactional
    public void save(ConfirmationToken confirmationToken) {
        em.persist(confirmationToken);
    }

    public Optional<ConfirmationToken> find(String id, LocalDateTime now, boolean expired) {
        return em.createQuery("select c from ConfirmationToken c where c.id = :id and c.expiredDate >= :expiredDate and c.expired = :expired", ConfirmationToken.class)
                .setParameter("id", id)
                .setParameter("expiredDate", now)
                .setParameter("expired", expired)
                .getResultList()
                .stream()
                .findFirst();
    }
}
