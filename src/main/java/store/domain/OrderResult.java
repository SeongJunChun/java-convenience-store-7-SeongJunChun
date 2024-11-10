package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.domain.product.Product;

public class OrderResult {

    List<Product> nonPromotionalProducts;
    List<Product> promotionalProducts;

    public OrderResult() {
        nonPromotionalProducts = new ArrayList<Product>();
        promotionalProducts = new ArrayList<Product>();
    }

    public int calculateTotalPrice() {
        return Stream.concat(nonPromotionalProducts.stream(), promotionalProducts.stream())
                .mapToInt(p -> p.getPrice() * p.getQuantity())
                .sum();
    }

    public int calculatePayment(Membership membership) {
        return calculateTotalPrice() - calculatePromotionDiscount() - calculateMembershipDiscount(membership);
    }

    public Map<String, Integer> getProductSummary() {
        return Stream.concat(nonPromotionalProducts.stream(), promotionalProducts.stream())
                .collect(Collectors.toMap(
                        Product::getName,
                        Product::getQuantity,
                        Integer::sum
                ));
    }

    public Map<String, Integer> getPromotionSummary() {
        return promotionalProducts.stream()
                .filter(product -> product.getQuantity() >= product.getPromotion().getPromotionThreshold())
                .collect(Collectors.toMap(
                        Product::getName,
                        product -> product.getQuantity() / product.getPromotion().getPromotionThreshold()
                ));
    }

    public int calculatePromotionDiscount() {
        return promotionalProducts.stream()
                .filter(product -> product.getQuantity() >= product.getPromotion().getPromotionThreshold())
                .mapToInt(product -> (product.getQuantity() / product.getPromotion().getPromotionThreshold())
                        * product.getPrice())
                .sum();
    }

    public int calculateMembershipDiscount(Membership membership) {
        int nonPromotionPrice = calculateTotalPrice() - calculateTotalPromotionPrice();
        return membership.calculateDiscount(nonPromotionPrice);
    }

    public int calculateTotalPromotionPrice() {
        return promotionalProducts.stream()
                .filter(product -> product.getQuantity() >= product.getPromotion().getPromotionThreshold())
                .mapToInt(product -> {
                    int nonPromotionQuantity = product.getQuantity() % product.getPromotion().getPromotionThreshold();
                    return product.getPrice() * (product.getQuantity() - nonPromotionQuantity);
                })
                .sum();
    }

    public void addProduct(Product product) {
        if (product.getPromotion() == null) {
            nonPromotionalProducts.add(product);
        }
        if (product.getPromotion() != null) {
            promotionalProducts.add(product);
        }
    }

}
