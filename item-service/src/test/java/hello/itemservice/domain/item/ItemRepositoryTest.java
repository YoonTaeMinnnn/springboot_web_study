package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test                         //given : 주어짐,  when : 테스트 목적(save), then : 결과비교
    void save(){
        //given
        Item item = new Item("itemA",10000,10);

        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        //given
        Item item1 = new Item("item1",10000,10);
        Item item2 = new Item("item2", 20000, 20);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(item1, item2);  //result가 item1, item2 가지고 있는지 테스트

    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("item1",10000,25);
        Item savedItem = itemRepository.save(item);
        Item updateParam = new Item("itemA", 10000, 10);

        //when
        itemRepository.update(savedItem.getId(), updateParam);

        //then
        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }
}
