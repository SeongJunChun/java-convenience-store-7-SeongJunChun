package store.domain.product;

import java.util.List;

public class AvailableProducts {

    private final List<Product> nonPromotionProducts;
    private final List<Product> promotionProducts;

    private AvailableProducts(List<Product> products) {
        this.nonPromotionProducts = getNonPromotionProducts(products);
        this.promotionProducts = getPromotionProducts(products);
    }

    public static AvailableProducts create(List<Product> products) {
        return new AvailableProducts(products);
    }

    public boolean isExist(String name) {
        Product nonPromotionProduct = findByName(name, nonPromotionProducts);
        return nonPromotionProduct != null;
    }

    public boolean isExistPromotionProduct(String name) {
        Product promotionProduct = findByName(name, promotionProducts);
        return promotionProduct != null;
    }

    public int getTotalQuantity(String name) {
        Product promotionProduct = findByName(name, promotionProducts);
        Product nonPromotionProduct = findByName(name, nonPromotionProducts);
        if (promotionProduct != null) {
            return nonPromotionProduct.getQuantity() + promotionProduct.getQuantity();
        }
        return nonPromotionProduct.getQuantity();
    }

    public int getPromotionQuantity(String name) {
        Product promotionProduct = findByName(name, promotionProducts);
        return promotionProduct.getQuantity();
    }

    private Product findByName(String name, List<Product> products) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private List<Product> getNonPromotionProducts(List<Product> products) {
        return products.stream()
                .filter(product -> product.getPromotion() == null)
                .toList();
    }

    private List<Product> getPromotionProducts(List<Product> products) {
        return products.stream()
                .filter(product -> product.getPromotion() != null)
                .toList();
    }
}
