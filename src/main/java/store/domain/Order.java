package store.domain;

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

    private void validatePositive(Map<String, Integer> orders) {
        List<String> invalidItems = orders.entrySet().stream()
                .filter(e -> e.getValue() < 0)
                .map(Map.Entry::getKey)
                .toList();

        if (!invalidItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateQuantity(Map<String, Integer> orders, AvailableProducts products) {
        List<String> invalidItems = orders.entrySet().stream()
                .filter(e -> e.getValue() > products.getTotalQuantity(e.getKey()))
                .map(Map.Entry::getKey)
                .toList();

        if (!invalidItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateExist(Map<String, Integer> orders, AvailableProducts products) {
        List<String> nonExistingItems = orders.keySet().stream()
                .filter(item -> !products.isExist(item))
                .toList();

        if (!nonExistingItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
