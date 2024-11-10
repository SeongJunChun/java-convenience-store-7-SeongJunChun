package store.service;

import store.domain.OrderResult;
import store.domain.product.AvailableProducts;
import store.domain.product.Product;
import store.domain.product.Products;

public class OrderService {
    private final Products products;
    private final OrderResult orderResult;
    private final AvailableProducts availableProducts;

    public OrderService(AvailableProducts availableProducts) {
        this.availableProducts = availableProducts;
        this.products = Products.getInstance();
        this.orderResult = new OrderResult();
    }

    public void buyNonPromotionProduct(String name, int amount) {
        if (amount > 0) {
            Product nonPromotionProduct = availableProducts.findNonPromotionProduct(name);
            Product product = createPuchaseProduct(nonPromotionProduct, amount);
            orderResult.addProduct(product);
            products.updateProduct(product);
        }
    }

    public void buyPromotionProduct(String name, int amount) {
        if (amount > 0) {
            Product promotionProduct = availableProducts.findPromotionProduct(name);
            Product product = createPuchaseProduct(promotionProduct, amount);
            orderResult.addProduct(product);
            products.updateProduct(product);
        }
    }

    private Product createPuchaseProduct(Product product, int amount) {
        return new Product.Builder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(amount)
                .promotion(product.getPromotion())
                .build();
    }

    public OrderResult getOrderResult() {
        return orderResult;
    }

}
