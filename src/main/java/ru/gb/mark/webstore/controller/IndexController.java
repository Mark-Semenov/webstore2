package ru.gb.mark.webstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.repository.BrandRepository;
import ru.gb.mark.webstore.service.CartService;
import ru.gb.mark.webstore.service.ProductService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final ProductService productService;
    private final BrandRepository brandRepository;
    private final CartService cartService;

    @ModelAttribute
    public void attributes(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("httpRequest", httpServletRequest);
    }


    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) Boolean error) {
        String errorMessage = null;
        if (error != null && error) {
            errorMessage = "invalid login or password";
        }
        model.addAttribute("error", errorMessage);
        return "login";
    }


    @GetMapping
    public String load(WebRequest webRequest, Model model) {
        Page<Product> products = productService.search(webRequest);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/add")
    public String addProductToCart(
            @RequestParam(name = "id") Long prodId,
            @RequestParam(required = false, name = "page") Integer page
    ) {
        cartService.addProductToCart(prodId);
        return "redirect:/?page=" + page;
    }


}
