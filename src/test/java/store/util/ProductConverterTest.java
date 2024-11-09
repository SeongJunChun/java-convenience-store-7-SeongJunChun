package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

class ProductConverterTest {

    private Promotions promotions;

    @BeforeEach
    void setUp() {
        Promotion soda = new Promotion.Builder()
                .name("탄산2+1")
                .buy(2)
                .get(1)
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,12,31))
                .build();
        Promotion md = new Promotion.Builder()
                .name("MD추천상품")
                .buy(1)
                .get(1)
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,12,31))
                .build();
        Promotion special = new Promotion.Builder()
                .name("반짝할인")
                .buy(1)
                .get(1)
                .startDate(LocalDate.of(2024,11,1))
                .endDate(LocalDate.of(2024,11,30))
                .build();
        List<Promotion> promotions = List.of(soda, md, special);
        this.promotions = new Promotions(promotions);
    }

    @DisplayName("문자열을 Product 객체로 변환한다")
    @Test
    void convertToProduct() {
        ProductConverter productConverter = new ProductConverter(promotions);
        Product product = productConverter.convertToObject("콜라,1000,10,탄산2+1");
        assertThat(product.getName()).isEqualTo("콜라");
    }

    @DisplayName("Product 객체를 문자열로 변환한다")
    @Test
    void convertToString() {
        ProductConverter productConverter = new ProductConverter(promotions);
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