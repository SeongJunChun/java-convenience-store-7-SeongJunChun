package store.service;

import store.domain.OrderResult;
import store.domain.product.AvailableProducts;
import store.domain.product.Product;
import store.domain.product.Products;

public class OrderService {
    private final Products products;
    private final OrderResult orderResult;
    private final AvailableProducts availableProducts;

    public OrderService(Products products, OrderResult orderResult, AvailableProducts availableProducts) {
        this.availableProducts = availableProducts;
        this.products = Products.getInstance();
        this.orderResult = orderResult;
    }

    public void buyNonPromotionProduct(String name, int amount) {
        Product nonPromotionProduct = availableProducts.findNonPromotionProduct(name);
        Product product = new Product.Builder()
                .name(nonPromotionProduct.getName())
                .price(nonPromotionProduct.getPrice())
                .quantity(amount)
                .promotion(null)
                .build();

        orderResult.addProduct(product);
        products.updateProduct(product);
    }

    public void buyPromotionProduct(String name, int amount) {
        Product promotionProduct = availableProducts.findPromotionProduct(name);
        Product product = new Product.Builder()
                .name(promotionProduct.getName())
                .price(promotionProduct.getPrice())
                .quantity(amount)
                .promotion(promotionProduct.getPromotion())
                .build();

        orderResult.addProduct(product);
        products.updateProduct(product);
    }

}
