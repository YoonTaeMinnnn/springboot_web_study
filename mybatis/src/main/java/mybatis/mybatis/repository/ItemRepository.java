package mybatis.mybatis.repository;

import lombok.RequiredArgsConstructor;
import mybatis.mybatis.domain.Item;
import mybatis.mybatis.repository.dto.ItemSearchCond;
import mybatis.mybatis.repository.dto.ItemUpdateDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final ItemMapper itemMapper;

    public Item save(Item item) {
        itemMapper.save(item);
        return item;
    }

    public void update(Long id, ItemUpdateDto itemUpdateDto) {
        itemMapper.update(id, itemUpdateDto);
    }

    public Optional<Item> findById(Long id) {
        return itemMapper.findById(id);
    }

    public List<Item> findAll(ItemSearchCond itemSearchCond) {
        return itemMapper.findAll(itemSearchCond);
    }
}
