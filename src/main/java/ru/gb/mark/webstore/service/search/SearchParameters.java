package ru.gb.mark.webstore.service.search;

import org.springframework.web.context.request.WebRequest;
import ru.gb.mark.webstore.repository.CategoryRepository;
import ru.gb.mark.webstore.repository.ProductRepository;

public record SearchParameters(
        WebRequest webRequest,
        ProductRepository repository,
        CategoryRepository categoryRepository
) {
}
