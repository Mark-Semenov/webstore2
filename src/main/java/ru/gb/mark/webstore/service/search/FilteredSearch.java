package ru.gb.mark.webstore.service.search;


import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.entity.Product;

import java.util.Map;
//Strategy pattern
@Log4j2
@Data
public class FilteredSearch implements SearchStrategy {

    private Pageable pageable;
    private final SearchParameters parameters;

    @Override
    public Page<Product> find() {
        String param;
        Map<String, String[]> map = parameters.webRequest().getParameterMap();

        for (String key : map.keySet()) {

            switch (key) {
                case "search" -> {
                    param = map.get("search")[0];
                    return parameters.repository().findAllByNameContainingIgnoreCase(param, Pageable.ofSize(5));
                }
                case "category" -> {
                    param = map.get("category")[0];
                    Category c = parameters.categoryRepository().findCategoryByName(param);
                    return parameters.repository().findByCategory(c, Pageable.ofSize(5));
                }
                case "page" -> {
                    int page = Integer.parseInt(map.get("page")[0]);
                    return parameters.repository().findAll(PageRequest.of(page, 5));
                }
            }
        }

        return Page.empty(Pageable.ofSize(0));
    }


}
