package store.util;

import store.domain.Product;

public class ProductConverter implements Converter<Product> {

    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

    @Override
    public String convertToString(Product product) {
        return String.join(DELIMITER,
                product.getName(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getQuantity()),
                product.getPromotion());
    }

    @Override
    public Product convertToObject(String line) {
        String[] data = line.split(DELIMITER);
        return new Product.Builder()
                .name(data[NAME_INDEX])
                .price(Integer.parseInt(data[PRICE_INDEX]))
                .quantity(Integer.parseInt(data[QUANTITY_INDEX]))
                .promotion(data[PROMOTION_INDEX])
                .build();
    }

}
