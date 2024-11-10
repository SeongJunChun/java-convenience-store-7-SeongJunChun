package store.domain.product;

import static store.message.ErrorMessage.NAME_CANNOT_BE_EMPTY;
import static store.message.ErrorMessage.STOCK_MUST_BE_NON_NEGATIVE;

import store.domain.promotion.Promotion;

public class Product {

    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.promotion = builder.promotion;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return "- "+String.join(" ",
                name,
                String.valueOf(price),
                quantityToString(),
                promotionToString()
        );
    }

    private String promotionToString() {
        if(promotion != null) {
            return promotion.getName();
        }
        return "";
    }

    private String quantityToString() {
        if(quantity == 0) {
            return "재고 없음";
        }
        return String.valueOf(quantity)+"개";
    }


    public static class Builder {
        private String name;
        private int price;
        private int quantity;
        private Promotion promotion;

        public Builder name(String name) {
            validateBlank(name);
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            validatePositiveNumber(price);
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            validatePositiveNumber(quantity);
            this.quantity = quantity;
            return this;
        }

        public Builder promotion(Promotion promotion) {
            this.promotion = promotion;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

        private void validatePositiveNumber(int number) {
            if (number < 0) {
                throw new IllegalStateException(STOCK_MUST_BE_NON_NEGATIVE.getMessage());
            }
        }

        private void validateBlank(String name) {
            if (name.isBlank()) {
                throw new IllegalStateException(NAME_CANNOT_BE_EMPTY.getMessage());
            }
        }

    }

}
