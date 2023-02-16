package ru.gb.mark.webstore.service.search;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Product;

@Data
@Component
public class Search {

    private Pageable pageable;
    private SearchStrategy searchStrategy;

    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public Page<Product> execute() {
        return searchStrategy.find();
    }

}
