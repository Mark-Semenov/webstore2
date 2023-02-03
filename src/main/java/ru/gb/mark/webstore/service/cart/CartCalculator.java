package ru.gb.mark.webstore.service.cart;

import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CartCalculator implements Calculator {

    @Override
    public BigDecimal calculateProductDiscount(Product product, Integer count) {
        BigDecimal discount = new BigDecimal(0);
        discount = discount
                .add(product.getOldPrice().subtract(product.getPrice()))
                .multiply(BigDecimal.valueOf(count));
        return discount;
    }

    @Override
    public BigDecimal calculateProductSum(Product product, Integer count) {
        BigDecimal sum = new BigDecimal(0);
        sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(count)));
        return sum;
    }


    @Override
    public int calculateTotalCount(Map<Product, Integer> products) {
        Integer totalCount = 0;
        for (Integer i : products.values()) {
            totalCount += i;
        }
        return totalCount;
    }

    @Override
    public BigDecimal calculateTotalDiscount(Map<Product, Integer> products) {
        BigDecimal discount = new BigDecimal(0);
        for (Product p : products.keySet()) {
            int count = products.get(p);
            discount = discount.add(p.getOldPrice().subtract(p.getPrice()).multiply(BigDecimal.valueOf(count)));
        }
        return discount;
    }

    @Override
    public BigDecimal calculateTotalSum(Map<Product, Integer> products) {
        BigDecimal sum = new BigDecimal(0);
        for (Product p : products.keySet()) {
            sum = sum.add(p.getPrice().multiply(BigDecimal.valueOf(products.get(p))));
        }
        return sum;
    }


}
