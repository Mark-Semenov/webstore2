package ru.gb.mark.webstore.service;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Order;
import ru.gb.mark.webstore.entity.OrderStatus;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.repository.OrderRepository;
import ru.gb.mark.webstore.service.cart.UserCart;

import java.util.List;

@Log4j2
@Component
public class CartService {

    private final OrderRepository orderRepository;
    private final UserCart userCart;


    public CartService(OrderRepository orderRepository, UserCart userCart) {
        this.orderRepository = orderRepository;
        this.userCart = userCart;
    }

    public void buyProducts(Order order) {
        order.setStatus(OrderStatus.NEW);
        order.setTotalSum(userCart.getTotalSum());
        saveOrder(order);
        userCart.clearCart();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void addToCart(@NonNull Long prodId) {
        Product p = userCart.getById(prodId);
        userCart.addToCart(p);
    }

    public void deleteProduct(@NotNull Long prodId) {
        userCart.removeProduct(userCart.getById(prodId));
    }

    public void removeOne(Long prodId) {
        userCart.decreaseProduct(userCart.getById(prodId));
    }

    public Integer getProductsCount() {
        return userCart.getTotalCount();
    }

    public Float getTotalSum() {
        return userCart.getTotalSum().floatValue();
    }

    public Float getTotalDiscount() {
        return userCart.getTotalDiscount().floatValue();
    }

    public List<Product> getProducts() {
        return userCart.getProductCart().keySet().stream().toList();
    }


    public Integer getOneProdCount(Product product) {
        return userCart.getProductCount(product);
    }
}
