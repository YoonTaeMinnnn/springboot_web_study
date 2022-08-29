package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    // pk 존재 -> save 실행x, merge 실행
    @Test
    public void save() {
        Item item = new Item("test");
        if (item.isNew()){
            em.persist(item);
        }
        itemRepository.save(item);
    }

}