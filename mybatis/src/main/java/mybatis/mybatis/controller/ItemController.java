package mybatis.mybatis.controller;

import lombok.RequiredArgsConstructor;
import mybatis.mybatis.domain.Item;
import mybatis.mybatis.repository.ItemRepository;
import mybatis.mybatis.repository.dto.ItemUpdateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/item")
    public void create() {
        Item item = new Item("란란루", 500, 5);
        itemRepository.save(item);
    }

    @PutMapping("/item")
    public void update() {
        ItemUpdateDto updateDto = new ItemUpdateDto("리리", 500, 10);
        itemRepository.update(4L, updateDto);
    }
}
