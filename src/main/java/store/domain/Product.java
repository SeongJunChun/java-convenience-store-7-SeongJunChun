package store.domain;

import java.time.LocalDate;
import store.domain.Promotion.Builder;

public class Product {

    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public static class Builder {
        private String name;
        private int price;
        private int quantity;
        private String promotion;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder promotion(String promotion) {
            this.promotion = promotion;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.promotion = builder.promotion;
    }

}
