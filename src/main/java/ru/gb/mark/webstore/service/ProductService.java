package ru.gb.mark.webstore.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import ru.gb.mark.webstore.dto.EntityMapper;
import ru.gb.mark.webstore.dto.ProductDTO;
import ru.gb.mark.webstore.entity.AdminPanelBlock;
import ru.gb.mark.webstore.entity.Brand;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.repository.AdminPanelBlockRepository;
import ru.gb.mark.webstore.repository.BrandRepository;
import ru.gb.mark.webstore.repository.ProductRepository;
import ru.gb.mark.webstore.service.search.ClearSearch;
import ru.gb.mark.webstore.service.search.FilteredSearch;
import ru.gb.mark.webstore.service.search.Search;
import ru.gb.mark.webstore.service.search.SearchParameters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Data
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandRepository brandRepository;
    private final FileService fileService;
    private final EntityMapper<Product, ProductDTO> entityMapper;
    private final Search search;
    private final AdminPanelBlockRepository adminPanelBlockRepository;

    public Page<Product> search(WebRequest webRequest) {

        SearchParameters parameters = new SearchParameters(webRequest, productRepository, categoryService);

        if (webRequest.getParameterMap().isEmpty()) {
            search.setSearchStrategy(new ClearSearch(parameters));
        } else {
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


    public List<ProductDTO> getAllProductsConvertedToDto() {
        return productRepository
                .findAll()
                .stream()
                .map(entityMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveProduct(ProductDTO productDTO) {

        fileService.setAndWriteImage(productDTO.getFile());

        String categoryName = productDTO.getCategoryName();
        String brandName = productDTO.getBrandName();
        Category category = categoryService.getCategoryByName(categoryName);
        Brand brand = brandRepository.findByTitle(brandName);

        productDTO.setBrand(brand);
        productDTO.setCategory(category);
        Product product = entityMapper.mapDtoToEntity(productDTO);
        productRepository.save(product);

    }

    public void updateProduct(ProductDTO productDTO) {
        saveProduct(productDTO);
    }

    @Transactional
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<String> getBrandsNames() {
        return brandRepository
                .findAll()
                .stream()
                .map(Brand::getTitle)
                .collect(Collectors.toList());
    }


}
