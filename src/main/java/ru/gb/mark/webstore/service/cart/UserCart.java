package ru.gb.mark.webstore.service.cart;

import ru.gb.mark.webstore.entity.Cart;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.entity.ProductInCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserCart {

    void loadSavedProducts(List<ProductInCart> products);

    void addToCart(Product product);

    void removeProduct(Product product);

    void decreaseProduct(Product product);

    void clearCart();

    Product getProductById(Long id);

    Map<Product, Integer> getProductCart();

    Integer getProductCount(Product product);

    Integer getTotalCount();

    BigDecimal getTotalSum();

    BigDecimal getTotalDiscount();

    BigDecimal getProductDiscount(Product product);

    void saveCart(Cart cart);



}
