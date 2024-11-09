package store.domain.product;

import static org.assertj.core.api.Assertions.*;


import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.PromotionTestBase;

class AvailableProductsTest extends PromotionTestBase {

    List<Product> products;

    @BeforeEach
    void setUp() {
        Product coke = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(9)
                .promotion(null)
                .build();
        Product cokePro = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(4)
                .promotion(promotions.findPromotionByName("탄산2+1"))
                .build();
        Product orangePro = new Product.Builder()
                .name("오렌지 주스")
                .price(1800)
                .quantity(9)
                .promotion(promotions.findPromotionByName("MD추천상품"))
                .build();
        Product orange = new Product.Builder()
                .name("오렌지 주스")
                .price(1800)
                .quantity(6)
                .promotion(null)
                .build();
        Product potato = new Product.Builder()
                .name("감자칩")
                .price(1500)
                .quantity(5)
                .promotion(null)
                .build();
        products = List.of(coke,cokePro, orange,orangePro, potato);
    }

    @DisplayName("해당 제품이 존재하면 true를 반환한다")
    @Test
    void existTest() {
        AvailableProducts availableProducts = AvailableProducts.create(products);
        assertThat(availableProducts.isExist("감자칩")).isTrue();
    }

    @DisplayName("해당 제품이 존재하지 않으면 false를 반환한다")
    @Test
    void notExistTest() {
        AvailableProducts availableProducts = AvailableProducts.create(products);
        assertThat(availableProducts.isExist("사이다")).isFalse();
    }

    @DisplayName("프로모션 제품이 존재하면 true 를 반환한다")
    @Test
    void existPromotionTest() {
        AvailableProducts availableProducts = AvailableProducts.create(products);
        assertThat(availableProducts.isExistPromotionProduct("오렌지 주스")).isTrue();
    }

    @DisplayName("프로모션 제품이 존재하지 않으면 false 를 반환한다")
    @Test
    void notExistPromotionTest() {
        AvailableProducts availableProducts = AvailableProducts.create(products);
        assertThat(availableProducts.isExistPromotionProduct("감자칩")).isFalse();
    }

    @DisplayName("프로모션 제품의 수량을 확인한다")
    @Test
    void getPromotionQuantityTest() {
        AvailableProducts availableProducts = AvailableProducts.create(products);
        assertThat(availableProducts.getPromotionQuantity("오렌지 주스")).isEqualTo(9);
    }


    @DisplayName("해당 제품의 전체 수량을 확인한다")
    @ParameterizedTest
    @MethodSource("items")
    void getTotalQuantityTest(String item,int expectedQuantity) {
        AvailableProducts availableProducts = AvailableProducts.create(products);
        assertThat(availableProducts.getTotalQuantity(item)).isEqualTo(expectedQuantity);
    }
    static Stream<Arguments> items() {
        return Stream.of(
                Arguments.of("콜라",13),
                Arguments.of("오렌지 주스",15),
                Arguments.of("감자칩",5)
        );
    }

}