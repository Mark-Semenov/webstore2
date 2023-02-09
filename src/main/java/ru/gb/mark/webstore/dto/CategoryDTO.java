package ru.gb.mark.webstore.dto;

import lombok.Builder;
import lombok.Data;
import ru.gb.mark.webstore.entity.Product;

import java.util.List;

@Data
@Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private List<Product> products;

}
