package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "/items/createItemForm";
    }

    @PostMapping("/new")
    public String create(BookForm bookForm) {
        Book item = new Book();
        item.setName(bookForm.getName());
        item.setPrice(bookForm.getPrice());
        item.setStockQuantity(bookForm.getStockQuantity());
        item.setAuthor(bookForm.getAuthor());
        item.setIsbn(bookForm.getIsbn());
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping
    public String itemList(Model model) {
        List<Item> items = itemService.findItem();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long id, Model model) {
        Book item = (Book) itemService.findOne(id);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "/items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(BookForm form){
        Book book = new Book();    //Book은 준영속 엔티티 (영속성 컨텍스트가 관리 x)
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }
}
