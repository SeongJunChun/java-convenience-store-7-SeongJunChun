package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.dto.OrderResultDto;
import store.dto.ProductSummaryDto;
import store.domain.product.Product;

public class OrderResult {

    private final List<Product> nonPromotionalProducts;
    private final List<Product> promotionalProducts;

    public OrderResult() {
        nonPromotionalProducts = new ArrayList<Product>();
        promotionalProducts = new ArrayList<Product>();
    }

    public OrderResultDto calculateTotalPriceAndQuantity() {
        int totalQuantity = Stream.concat(nonPromotionalProducts.stream(), promotionalProducts.stream())
                .mapToInt(Product::getQuantity)
                .sum();

        int totalPrice = Stream.concat(nonPromotionalProducts.stream(), promotionalProducts.stream())
                .mapToInt(p -> p.getPrice() * p.getQuantity())
                .sum();

        return new OrderResultDto(totalQuantity, totalPrice);
    }

    public int calculatePayment(Membership membership) {
        int totalPrice = calculateTotalPriceAndQuantity().getTotalPrice();
        return totalPrice - calculatePromotionDiscount() - calculateMembershipDiscount(membership);
    }

    public List<ProductSummaryDto> getProductSummary() {
        Map<String, ProductSummaryDto> productSummaryMap = createProductSummaryMap();
        return new ArrayList<>(productSummaryMap.values());
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
        int nonPromotionPrice = calculateTotalPriceAndQuantity().getTotalPrice() - calculateTotalPromotionPrice();
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

    private Map<String, ProductSummaryDto> createProductSummaryMap() {
        return Stream.concat(nonPromotionalProducts.stream(), promotionalProducts.stream())
                .collect(Collectors.toMap(
                        Product::getName,
                        this::createProductSummary,
                        this::mergeProductSummaries
                ));
    }

    private ProductSummaryDto createProductSummary(Product product) {
        int totalPrice = product.getQuantity() * product.getPrice();
        return new ProductSummaryDto(product.getName(), product.getQuantity(), totalPrice);
    }

    private ProductSummaryDto mergeProductSummaries(ProductSummaryDto summary1, ProductSummaryDto summary2) {
        int combinedQuantity = summary1.getQuantity() + summary2.getQuantity();
        int combinedTotalPrice = summary1.getTotalPrice() + summary2.getTotalPrice();
        return new ProductSummaryDto(summary1.getName(), combinedQuantity, combinedTotalPrice);
    }

}
