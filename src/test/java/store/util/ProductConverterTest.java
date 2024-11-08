package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;

class ProductConverterTest {

    @DisplayName("문자열을 Product 객체로 변환한다")
    @Test
    void convertToProduct() {
        ProductConverter productConverter = new ProductConverter();
        Product product = productConverter.convertToObject("콜라,1000,10,탄산2+1");
        assertThat(product.getName()).isEqualTo("콜라");
    }

    @DisplayName("Product 객체를 문자열로 변환한다")
    @Test
    void convertToString() {
        ProductConverter productConverter = new ProductConverter();
        Product product = new Product.Builder()
                .name("오렌지 에이드")
                .price(1500)
                .quantity(9)
                .promotion(null)
                .build();
        String line = productConverter.convertToString(product);
        assertThat(line).isEqualTo("오렌지 에이드,1500,9,null");
    }

}