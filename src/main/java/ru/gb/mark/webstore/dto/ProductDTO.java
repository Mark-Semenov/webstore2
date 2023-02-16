package ru.gb.mark.webstore.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.mark.webstore.entity.Brand;
import ru.gb.mark.webstore.entity.Category;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String status;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private Boolean sale;
    private String image;
    private Integer count;
    private String categoryName;
    private String brandName;
    private MultipartFile file;
    private Category category;
    private Brand brand;
}
