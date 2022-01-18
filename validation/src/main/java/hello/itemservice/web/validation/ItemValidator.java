package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    // 여러 검증기 사용 시, 검증 대상(@Validated가 붙은 클래스)를 지원하는 지 여부를 판단
    // true 반환 시, 검증기 사용
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Item item = (Item) target;

        //검증로직
        if (!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }
        if (item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price","range", new Object[]{1000,1000000},null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity","max", new Object[]{9999}, null);
        }
        //특정필드가 아닌 복합룰 검증 (ObjectError)
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


    }
}
