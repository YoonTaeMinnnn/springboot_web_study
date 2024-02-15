package mybatis.mybatis.repository;

import mybatis.mybatis.domain.Item;
import mybatis.mybatis.repository.dto.ItemSearchCond;
import mybatis.mybatis.repository.dto.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {


    void save(Item item);

    // param이 두개 이상인 경우는 어노테이션 필요! + 쿼리 작성 시, updateParam.itemName 으로 작성해야댐
    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    List<Item> findAll(ItemSearchCond itemSearch);

    Optional<Item> findById(Long id);
}
