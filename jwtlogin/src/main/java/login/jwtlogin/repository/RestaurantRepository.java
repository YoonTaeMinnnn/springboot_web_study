package login.jwtlogin.repository;

import login.jwtlogin.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class RestaurantRepository {

    private final EntityManager em;

    @Transactional
    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    public Restaurant findById(Long id) {
        return em.find(Restaurant.class, id);
    }

}
