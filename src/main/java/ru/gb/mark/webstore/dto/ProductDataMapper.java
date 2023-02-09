package ru.gb.mark.webstore.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Product;

@Log4j2
@Data
@Component //Data Mapper
public class ProductDataMapper implements EntityMapper<Product, ProductDTO> {

    @Override
    public Product mapDtoToEntity(ProductDTO dto) {

        return ProductWrapper
                .builder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setCount(dto.getCount())
                .setStatus(dto.getStatus())
                .setPrice(dto.getPrice())
                .setOldPrice(dto.getOldPrice())
                .setImage(dto.getFile().getOriginalFilename())
                .setBrand(dto.getBrand())
                .setCategory(dto.getCategory())
                .build();
    }

    @Override
    public ProductDTO mapEntityToDto(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .oldPrice(product.getOldPrice())
                .image(product.getImage())
                .sale(product.getSale())
                .count(product.getCount())
                .status(product.getStatus())
                .brandName(product.getBrand().getTitle())
                .categoryName(product.getCategory().getName())
                .build();
    }
}

