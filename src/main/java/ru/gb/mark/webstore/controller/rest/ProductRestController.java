package ru.gb.mark.webstore.controller.rest;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.service.ProductService;

import java.net.URI;
import java.util.Optional;

@Data
@RestController()
@RequestMapping("/api/v1/")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/prod")
    public Optional<Product> getProduct(@RequestParam(name = "id") Long id) {
        return productService.findProductById(id);
    }

    @GetMapping("/products")
    public ResponseEntity<?> products() {

        return ResponseEntity
                .created(URI.create("/api/v1/products"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getAllProductsConvertedToDto());
    }


}
