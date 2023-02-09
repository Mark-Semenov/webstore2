package ru.gb.mark.webstore.service.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.gb.mark.webstore.entity.Product;

public record ClearSearch(SearchParameters parameters) implements SearchStrategy {
    @Override
    public Page<Product> find() {
        return parameters.repository().findAll(buildPageable());
    }

    private Pageable buildPageable() {
        return PageRequest.of(0, 4,
                Sort.by(Sort.Direction.ASC, "id"));
    }
}
