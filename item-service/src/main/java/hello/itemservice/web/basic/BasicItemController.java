package hello.itemservice.web.basic;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")  //컨트롤러의 모든 메소드에 적용됨 model에 값넣어짐(리턴값을)
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode(("FAST"), "빠른 배송"));
        deliveryCodes.add(new DeliveryCode(("NORMAL"), "일반 배송"));
        deliveryCodes.add(new DeliveryCode(("SLOW"), "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(Item item){

        itemRepository.save(item);

        //model.addAttribute("item", item);

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){ //item에 model 넣어줌 (자동으로)
        itemRepository.save(item);

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){ //Item 클래스 => "item" 이란 모델에 넣어줌 (자동으로)
        itemRepository.save(item);

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item){ // 어노테이션도 생략가능
        itemRepository.save(item);

        //model.addAttribute("item", item);

        return "/basic/item";
    }

    //@PostMapping("/add")             //리다이렉트 (Post/Redirect/Get)
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();  //get요청으로 보냄
    }

    @PostMapping("/add")
    public String addItemV6(Item item , RedirectAttributes redirectAttributes){
        log.info("item.open={}", item.getOpen());
        log.info("item.regions = {}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());
        log.info("item.deliveryCode={}", item.getDeliveryCode());
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId")long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/item";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";  //PathVariable에 있는 itemId 사용가능
    }




    @PostConstruct  // 의존성 주입이 끝나고, 테스트 목적으로 샘플데이터를 넣어둠
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

    @ResponseBody
    @PostMapping("/test")
    public Item test(@RequestBody Item item){

        return item;
    }



}
