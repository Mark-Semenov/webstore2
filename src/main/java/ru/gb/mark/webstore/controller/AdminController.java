package ru.gb.mark.webstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.mark.webstore.dto.*;
import ru.gb.mark.webstore.service.CategoryService;
import ru.gb.mark.webstore.service.OrderService;
import ru.gb.mark.webstore.service.ProductService;
import ru.gb.mark.webstore.service.UserService;

import java.util.Arrays;

@Log4j2
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;


    @ModelAttribute
    public void attributes(Model model) {
        model.addAttribute("blocks", productService.getAdminBlocks());
        model.addAttribute("categories", categoryService.getNamesOfCategories());
        model.addAttribute("brands", productService.getBrandsNames());
        model.addAttribute("roles", Arrays.stream(AppRoles.values()).limit(2));
        model.addAttribute("statuses", OrderStatus.values());

    }

    @GetMapping
    public String welcome() {
        return "admin";
    }

    @GetMapping("/user/new")
    public String createUser(Model model) {
        model.addAttribute("userDTO",
                UserDTO.builder().build());
        return "admin";
    }

    @PostMapping("/user/new")
    public String addNewUser(UserDTO user) {
        userService.registerNewUserAccount(user);
        return "admin";
    }

    @GetMapping("/product/new")
    public String createProduct(Model model) {
        model.addAttribute("product",
                ProductDTO.builder().build());
        return "admin";
    }

    @PostMapping("/product/new/product")
    public String addNewProduct(ProductDTO product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }


    @GetMapping("/products")
    public String showAllProducts(Model model) {
        model.addAttribute("products",
                productService.getAllProductsConvertedToDto());
        return "admin";
    }

    @GetMapping("/products/remove/{id}")
    public String removeProduct(@PathVariable(name = "id") Long id) {
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/update/{id}")
    public String updateProduct(Model model, @PathVariable(name = "id") Long id) {
        ProductDTO productDTO = productService
                .getEntityMapper()
                .mapEntityToDto(productService.findProductById(id).orElseThrow());
        model.addAttribute("product", productDTO);
        return "update";
    }

    @PostMapping("/update/product")
    public String updateProduct(ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/orders")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.findOrders());
        return "admin";
    }


    @PostMapping("/orders/update/status")
    public String changeOrderStatus(String statusName, Long orderId) {
        orderService.updateOrderStatus(statusName, orderId);
        return "redirect:/admin/orders";
    }

    @GetMapping("/category/new")
    public String addNewCategory(Model model) {
        model.addAttribute("newCategory", CategoryDTO.builder().build());
        return "admin";
    }

    @PostMapping("/category/new")
    public String saveNewCategory(CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
        return "redirect:/admin";
    }


}
