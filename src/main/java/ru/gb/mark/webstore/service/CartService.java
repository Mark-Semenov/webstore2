package ru.gb.mark.webstore.service;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.dto.EntityMapper;
import ru.gb.mark.webstore.dto.OrderDTO;
import ru.gb.mark.webstore.entity.Order;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.service.cart.UserCart;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@Data
@Component
public class CartService {

    private final OrderService orderService;
    private final UserService userService;
    private final UserCart userCart;

    private final EntityMapper<Order, OrderDTO> mapper;


    public void checkout(OrderDTO orderDTO) {
        //TODO
        userCart.clearCart();
    }

    public void saveOrder(Order order) {
        orderService.saveOrder(order);
    }

    public void addProductToCart(@NonNull Long id) {
        userCart.addToCart(userCart.getProductById(id));
    }

    public void deleteProduct(@NotNull Long prodId) {
        userCart.removeProduct(userCart.getProductById(prodId));
    }

    public void removeOne(Long prodId) {
        userCart.decreaseProduct(userCart.getProductById(prodId));
    }

    public Integer getProductsCount() {
        return userCart.getTotalCount();
    }

    public BigDecimal getTotalSum() {
        return userCart.getTotalSum();
    }

    public BigDecimal getTotalDiscount() {
        return userCart.getTotalDiscount();
    }

    public List<Product> getProducts() {
        return userCart.getProductCart().keySet().stream().toList();
    }


    public Integer getCountOfProduct(Product product) {
        return userCart.getProductCount(product);
    }
}
