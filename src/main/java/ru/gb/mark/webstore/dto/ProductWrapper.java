package ru.gb.mark.webstore.dto;

import lombok.Data;
import ru.gb.mark.webstore.entity.Brand;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.entity.Product;

import java.math.BigDecimal;

@Data
public class ProductWrapper {

    public static Builder builder() {
        return new Builder();
    }

    static class Builder implements Wrapper<Product> {
        private final Product product;

        public Builder() {
            this.product = new Product();
        }

        public Builder setId(Long id) {
            if (id != null) {
                this.product.setId(id);
            }
            return this;
        }


        public Builder setName(String name) {
            this.product.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            this.product.setDescription(description);
            return this;
        }

        public Builder setStatus(String status) {
            this.product.setStatus(status);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.product.setPrice(price);
            return this;
        }

        public Builder setOldPrice(BigDecimal oldPrice) {
            this.product.setOldPrice(oldPrice);
            return this;
        }

        public Builder setSale(Boolean sale) {
            this.product.setSale(sale);
            return this;
        }

        public Builder setImage(String image) {
            this.product.setImage(image);
            return this;
        }

        public Builder setCount(Integer count) {
            this.product.setCount(count);
            return this;
        }

        public Builder setCategory(Category category) {
            this.product.setCategory(category);
            return this;
        }

        public Builder setBrand(Brand brand) {
            this.product.setBrand(brand);
            return this;
        }

        @Override
        public Product build() {
            return this.product;
        }


    }
}
