package ru.gb.mark.webstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Page<Product> findAllByPriceBetween(BigDecimal price, BigDecimal price2, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    void save(Product product);

    void deleteById(Long id);


}
