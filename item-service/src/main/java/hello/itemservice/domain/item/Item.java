package hello.itemservice.domain.item;


import lombok.Data;

import java.util.List;

@Data
public class Item {  //form으로부터 입력받아오는 형태의 데이터
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean open;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public Item(){

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
