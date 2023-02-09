package ru.gb.mark.webstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.mark.webstore.dto.OrderDTO;
import ru.gb.mark.webstore.dto.OrderStatus;
import ru.gb.mark.webstore.entity.User;
import ru.gb.mark.webstore.service.CartService;
import ru.gb.mark.webstore.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@EnableMethodSecurity
public class CartController {

    private final UserService userService;
    private final CartService cartService;


    @ModelAttribute
    public void attributes(Model model, HttpServletRequest request) {
        model.addAttribute("status", OrderStatus.NEW);
        model.addAttribute("cart", cartService.getProducts());
        model.addAttribute("totalSum", cartService.getTotalSum());
        model.addAttribute("totalDiscount", cartService.getTotalDiscount());
        model.addAttribute("totalCount", cartService.getProductsCount());
        model.addAttribute("httpRequest", request);
    }

    @GetMapping
    public String cart(Principal principal) {
        if (principal != null) {
            Optional<User> user = userService.findUserByEmail(principal.getName());
            user.ifPresent(usr -> userService.saveUserCartWithProducts(usr.getCart()));

        }
        return "cart";
    }

    @GetMapping("/add")
    public String addProductToCart(@RequestParam(name = "id") Long id) {
        cartService.addProductToCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/delete")
    public String totalRemoveFromCart(@RequestParam(name = "id") Long prodId) {
        cartService.deleteProduct(prodId);
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeOne(@RequestParam(name = "id") Long prodId) {
        cartService.removeOne(prodId);
        return "redirect:/cart";
    }

    @GetMapping("/order")
    public String buyProducts(Model model) {
        model.addAttribute("order", OrderDTO.builder().build());
        return "order";
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping("/order/checkout")
    public String makeOrder(OrderDTO orderDTO) {
        cartService.checkout(orderDTO);
        return "order";

    }

}
