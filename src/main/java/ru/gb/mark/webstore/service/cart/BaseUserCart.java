package ru.gb.mark.webstore.service.cart;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.gb.mark.webstore.entity.Cart;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.entity.ProductInCart;
import ru.gb.mark.webstore.repository.CartRepository;
import ru.gb.mark.webstore.repository.ProductInCartRepository;
import ru.gb.mark.webstore.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
@Data
@SessionScope
public class BaseUserCart implements UserCart {

    private Map<Product, Integer> productCart;
    private final Calculator calculator;
    private final ProductService productService;
    private final ProductInCartRepository productInCartRepository;
    private final CartRepository cartRepository;


    @PostConstruct
    void init() {
        productCart = new HashMap<>();
    }

    @Override
    public void loadSavedProducts(List<ProductInCart> products) {
        for (ProductInCart p : products) {
            productCart.put(p.getProduct(), p.getCount());
        }
    }

    @Override
    public void addToCart(Product product) {

        int count;
        if (productCart.containsKey(product)) {
            count = productCart.get(product);
            count++;
            productCart.put(product, count);
        } else productCart.put(product, 1);

    }

    @Override
    public void removeProduct(Product product) {
        productCart.remove(product);
    }

    @Override
    public void decreaseProduct(Product product) {
        if (productCart.containsKey(product)) {
            int count = productCart.get(product);
            if (count > 1) {
                productCart.put(product, --count);
            } else {
                removeProduct(product);
            }
        }
    }

    @Override
    public void clearCart() {
        productCart.clear();
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productCart
                .keySet()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (product == null) {
            return productService.findProductById(id).orElseThrow();
        }
        return product;
    }

    @Override
    public Map<Product, Integer> getProductCart() {
        return productCart;
    }


    @Override
    public Integer getProductCount(Product product) {
        return productCart.getOrDefault(product, 0);

    }

    @Override
    public Integer getTotalCount() {
        return calculator.calculateTotalCount(productCart);
    }

    @Override
    public BigDecimal getTotalSum() {
        return calculator.calculateTotalSum(productCart);
    }

    @Override
    public BigDecimal getTotalDiscount() {
        return calculator.calculateTotalDiscount(productCart);
    }

    @Override
    public BigDecimal getProductDiscount(Product product) {
        return calculator.calculateProductDiscount(product, productCart.get(product));
    }

    @Override
    public void saveCart(Cart cart) {
        ProductInCart product;
        List<ProductInCart> temp = new ArrayList<>();
        if (!productCart.keySet().isEmpty()) {
            for (Product p : productCart.keySet()) {
                product = new ProductInCart(p, getProductCount(p));
                productInCartRepository.save(product);
                temp.add(product);
            }
            cart.setProducts(temp);
            cartRepository.save(cart);
        } else cart.setProducts(null);

    }

}
