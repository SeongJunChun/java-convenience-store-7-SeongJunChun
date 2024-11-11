package store.util;

import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

public class ProductConverter implements Converter<Product> {

    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

    private final Promotions promotions;

    public ProductConverter(Promotions promotions) {
        this.promotions = promotions;
    }

    @Override
    public String convertToString(Product product) {
        return String.join(DELIMITER,
                product.getName(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getQuantity()),
                getPromotionName(product.getPromotion()));
    }

    @Override
    public Product convertToObject(String line) {
        String[] data = line.split(DELIMITER);
        return new Product.Builder()
                .name(data[NAME_INDEX])
                .price(Integer.parseInt(data[PRICE_INDEX]))
                .quantity(Integer.parseInt(data[QUANTITY_INDEX]))
                .promotion(promotions.findPromotionByName(data[PROMOTION_INDEX]))
                .build();
    }

    private String getPromotionName(Promotion promotion) {
        if (promotion == null) {
            return null;
        }
        return promotion.getName();
    }

}
