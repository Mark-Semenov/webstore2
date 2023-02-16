package ru.gb.mark.webstore.service.search;

import org.springframework.web.context.request.WebRequest;
import ru.gb.mark.webstore.repository.ProductRepository;
import ru.gb.mark.webstore.service.CategoryService;

public record SearchParameters(
        WebRequest webRequest,
        ProductRepository repository,
        CategoryService categoryService
) {
}
