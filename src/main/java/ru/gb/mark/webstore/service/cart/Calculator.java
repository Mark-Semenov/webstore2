package ru.gb.mark.webstore.service.cart;

import ru.gb.mark.webstore.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface Calculator {


    BigDecimal calculateProductDiscount(Product product, Integer count);

    BigDecimal calculateProductSum(Product product, Integer count);

    int calculateTotalCount(Map<Product, Integer> products);

    BigDecimal calculateTotalDiscount(Map<Product, Integer> products);

    BigDecimal calculateTotalSum(Map<Product, Integer> products);


}
