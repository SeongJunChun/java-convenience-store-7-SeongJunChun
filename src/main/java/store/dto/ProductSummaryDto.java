package store.dto;

import java.util.Objects;

public class ProductSummaryDto {

    private final String name;
    private final int quantity;
    private final int totalPrice;

    public ProductSummaryDto(String name, int quantity, int totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductSummaryDto that = (ProductSummaryDto) o;
        return quantity == that.quantity &&
                totalPrice == that.totalPrice &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, totalPrice);
    }

}
