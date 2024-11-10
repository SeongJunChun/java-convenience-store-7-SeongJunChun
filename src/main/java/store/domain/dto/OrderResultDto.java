package store.domain.dto;

public class OrderResultDto {
    private final int totalQuantity;
    private final int totalPrice;

    public OrderResultDto(int totalQuantity, int totalPrice) {
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}

