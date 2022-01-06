package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional    //변경감지기능 이용
    public Item updateItem(Long itemId, Book bookParam) {
        Item findItem = itemRepository.findOne(itemId);     //findItem 은 영속상태 ==> set해주면 알아서 트랜잭션이 커밋되면서 알아서 업데이트 해줌(변경된 내용)
        findItem.setPrice(bookParam.getPrice());
        findItem.setName(bookParam.getName());
        findItem.setStockQuantity(bookParam.getStockQuantity());
        return findItem;    //findItem => 영속성 컨텍스트 관리
    }

    public List<Item> findItem(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

}
