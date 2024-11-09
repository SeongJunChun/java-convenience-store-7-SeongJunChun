package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductsTest extends PromotionTestBase {

    private Products products;

    @BeforeEach
    void setUp(){
        Product coke = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(10)
                .promotion(promotions.findPromotionByName("탄산2+1"))
                .build();
        Product orange = new Product.Builder()
                .name("오렌지 주스")
                .price(1800)
                .quantity(9)
                .promotion(promotions.findPromotionByName("MD추천상품"))
                .build();
        Product potato = new Product.Builder()
                .name("감자칩")
                .price(1500)
                .quantity(5)
                .promotion(promotions.findPromotionByName("반짝할인"))
                .build();
        products = Products.getInstance(List.of(coke, orange, potato));
    }

    @DisplayName("프로모션에 진행중인 상품 목록을 모두 불러온다(일반 제고 포함)")
    @Test
    void findPromotionByDate() {
        Set<Promotion> promotionSet = new HashSet<>();
        promotionSet.add(promotions.findPromotionByName("탄산2+1"));
        List<Product> allProductsByPromotion = products.findAllProductsByPromotion(promotionSet);
        assertThat(allProductsByPromotion.size()).isEqualTo(4);
    }
}