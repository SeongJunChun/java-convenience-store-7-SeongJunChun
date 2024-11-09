package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.domain.product.Product;

public class OrderResult {

    List<Product> nonPromotionalProducts;
    List<Product> promotionalProducts;

    public OrderResult() {
        nonPromotionalProducts = new ArrayList<Product>();
        promotionalProducts = new ArrayList<Product>();
    }

    public void addNonPromotionalProduct(Product product) {
        nonPromotionalProducts.add(product);
    }

    public void addPromotionalProduct(Product product) {
        promotionalProducts.add(product);
    }
}
