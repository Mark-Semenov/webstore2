package ru.gb.mark.webstore.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.mark.webstore.service.ProductService;

import java.util.Optional;

@RestController(value = "/api/v1")
public class ProductRestController {

    private final ProductService productService;


    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/prod")
    public ResponseEntity<?> getProduct(@RequestParam(name = "id") Long id) {
        return ResponseEntity.of(productService.findProductById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<?> products() {
        return ResponseEntity.of(Optional.ofNullable(productService.getProducts()));
    }


}
