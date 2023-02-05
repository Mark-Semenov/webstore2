package ru.gb.mark.webstore.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import ru.gb.mark.webstore.entity.AdminPanelBlock;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.repository.AdminPanelBlockRepository;
import ru.gb.mark.webstore.repository.CategoryRepository;
import ru.gb.mark.webstore.repository.ProductRepository;
import ru.gb.mark.webstore.service.search.ClearSearch;
import ru.gb.mark.webstore.service.search.FilteredSearch;
import ru.gb.mark.webstore.service.search.Search;
import ru.gb.mark.webstore.service.search.SearchParameters;

import java.util.List;
import java.util.Optional;

@Component
@Data
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final Search search;
    private final AdminPanelBlockRepository adminPanelBlockRepository;

    //Strategy pattern used
    public Page<Product> search(WebRequest webRequest) {

        SearchParameters parameters = new SearchParameters(webRequest, productRepository, categoryRepository);

        if (webRequest.getParameterMap().isEmpty()) {
            log.info("CLEAR SEARCH");
            search.setSearchStrategy(new ClearSearch(parameters));
        } else {
            log.info("FILTERED SEARCH");
            search.setSearchStrategy(new FilteredSearch(parameters));

        }

        return search.execute();

    }

    public List<AdminPanelBlock> getAdminBlocks() {
        return adminPanelBlockRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

}
