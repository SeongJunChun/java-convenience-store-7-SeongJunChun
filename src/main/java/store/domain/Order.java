package store.domain;

import static store.message.ErrorMessage.EXCEEDS_STOCK_QUANTITY;
import static store.message.ErrorMessage.NON_EXISTENT_PRODUCT;
import static store.message.ErrorMessage.QUANTITY_MUST_BE_ONE_OR_MORE;

import java.util.List;
import java.util.Map;

import store.domain.product.AvailableProducts;

public class Order {

    private final Map<String, Integer> orders;

    private Order(Map<String, Integer> orders, AvailableProducts products) {
        validateExist(orders, products);
        validateQuantity(orders, products);
        validatePositive(orders);
        this.orders = orders;
    }

    public static Order createOrder(Map<String, Integer> orders, AvailableProducts products) {
        return new Order(orders, products);
    }

    public Map<String, Integer> getOrders() {
        return orders;
    }

    private void validatePositive(Map<String, Integer> orders) {
        List<String> invalidItems = orders.entrySet().stream()
                .filter(e -> e.getValue() <= 0)
                .map(Map.Entry::getKey)
                .toList();

        if (!invalidItems.isEmpty()) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_ONE_OR_MORE.getMessageWithRetry());
        }
    }

    private void validateQuantity(Map<String, Integer> orders, AvailableProducts products) {
        List<String> invalidItems = orders.entrySet().stream()
                .filter(e -> e.getValue() > products.getTotalQuantity(e.getKey()))
                .map(Map.Entry::getKey)
                .toList();

        if (!invalidItems.isEmpty()) {
            throw new IllegalArgumentException(EXCEEDS_STOCK_QUANTITY.getMessageWithRetry());
        }
    }

    private void validateExist(Map<String, Integer> orders, AvailableProducts products) {
        List<String> nonExistingItems = orders.keySet().stream()
                .filter(item -> !products.isExist(item))
                .toList();

        if (!nonExistingItems.isEmpty()) {
            throw new IllegalArgumentException(NON_EXISTENT_PRODUCT.getMessageWithRetry());
        }
    }
}
