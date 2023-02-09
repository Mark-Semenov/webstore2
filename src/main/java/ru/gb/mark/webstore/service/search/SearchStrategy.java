package ru.gb.mark.webstore.service.search;

import org.springframework.data.domain.Page;
import ru.gb.mark.webstore.entity.Product;

public interface SearchStrategy {

    Page<Product> find();

}
