package ru.gb.mark.webstore.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Category;

@Log4j2
@Data
@Component //Data Mapper
public class CategoryDataMapper implements EntityMapper<Category, CategoryDTO> {

    @Override
    public Category mapDtoToEntity(CategoryDTO dto) {

        Category category = new Category();
        category.setName(dto.getName());
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }

        return category;

    }

    @Override
    public CategoryDTO mapEntityToDto(Category product) {
        CategoryDTO categoryDTO = CategoryDTO.builder().build();
        categoryDTO.setName(product.getName());
        return categoryDTO;
    }
}

