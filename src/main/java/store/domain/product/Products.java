package store.domain.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import store.domain.promotion.Promotion;

public class Products {

    private final List<Product> products;
    private static Products instance;

    private Products(List<Product> products) {
        this.products = List.copyOf(getAllProducts(products));
    }

    public static Products getInstance(List<Product> products) {
        if (instance == null) {
            instance = new Products(products);
        }
        return instance;
    }

    public static Products getInstance() {
        if (instance == null) {
            instance = new Products(List.of());
        }
        return instance;
    }

    public List<Product> findAllProductsByPromotion(Set<Promotion> promotions) {
        return products.stream()
                .filter(product -> promotions.contains(product.getPromotion()) || product.getPromotion() == null)
                .toList();
    }

    private List<Product> getAllProducts(List<Product> products) {
        Set<String> nonPromotionProducts = getNonPromotionProductNames(products);
        List<Product> newProducts = new ArrayList<>();
        products.forEach(product -> {
            newProducts.add(product);
            if (product.getPromotion() != null && !nonPromotionProducts.contains(product.getName())) {
                newProducts.add(createNonPromotionProducts(product));
            }
        });
        return newProducts;
    }

    private Product createNonPromotionProducts(Product product) {
        return new Product.Builder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(0)
                .promotion(null)
                .build();
    }

    private Set<String> getNonPromotionProductNames(List<Product> products) {
        return products.stream()
                .filter(product -> product.getPromotion() == null)
                .map(Product::getName)
                .collect(Collectors.toSet());
    }

}
