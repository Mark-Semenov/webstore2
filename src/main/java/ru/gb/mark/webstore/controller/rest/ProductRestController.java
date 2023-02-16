package ru.gb.mark.webstore.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.mark.webstore.service.ProductService;

import java.net.URI;

@Data
@RestController()
@RequestMapping("/api/v1/products/")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/product/get")
    public ResponseEntity<?> getProduct(@RequestParam(name = "id") Long id, HttpServletRequest request) {
        return ResponseEntity
                .created(URI.create(request.getRequestURI()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getEntityMapper().mapEntityToDto(
                        productService.findProductById(id).orElse(null)));
    }

    @GetMapping("/")
    public ResponseEntity<?> products() {
        return ResponseEntity
                .created(URI.create("/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getAllProductsConvertedToDto());
    }


}
