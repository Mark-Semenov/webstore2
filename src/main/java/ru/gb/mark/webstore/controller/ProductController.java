package ru.gb.mark.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.mark.webstore.dto.ProductDTO;
import ru.gb.mark.webstore.service.CartService;
import ru.gb.mark.webstore.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;


    @GetMapping
    public String productInfo(Model model, @RequestParam(name = "id", required = false) Long id) {
        ProductDTO product = productService
                .getEntityMapper()
                .mapEntityToDto(productService.findProductById(id).orElse(null));
        model.addAttribute("product", product);
        return "product";
    }


    @GetMapping("/add")
    public String addProductToCart(@RequestParam(name = "id") Long id) {
        cartService.addProductToCart(id);
        return String.format("redirect:/product?id=%d", id);
    }


}
