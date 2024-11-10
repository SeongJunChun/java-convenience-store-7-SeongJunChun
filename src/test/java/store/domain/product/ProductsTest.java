package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.PromotionTestBase;
import store.domain.product.Product.Builder;
import store.domain.promotion.Promotion;

class ProductsTest extends PromotionTestBase {

    private Products products;

    @BeforeEach
    void setUp(){
        Products.resetInstance();
        Product coke = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(10)
                .promotion(promotions.findPromotionByName("탄산2+1"))
                .build();
        Product nonPromotionCoke = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(7)
                .promotion(null)
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
        products = Products.getInstance(List.of(coke,nonPromotionCoke, orange, potato));
    }

    @DisplayName("프로모션에 진행중인 상품 목록을 모두 불러온다(일반 제고 포함)")
    @Test
    void findPromotionByDate() {
        Set<Promotion> promotionSet = new HashSet<>();
        promotionSet.add(promotions.findPromotionByName("탄산2+1"));
        List<Product> allProductsByPromotion = products.findAllProductsByPromotion(promotionSet);
        assertThat(allProductsByPromotion.size()).isEqualTo(4);
    }

    @DisplayName("상품 구매후 감소된 수량을 확인한다")
    @Test
    void updateProductTest() {
        Product coke = new Builder()
                .name("콜라")
                .price(1000)
                .quantity(5)
                .promotion(promotions.findPromotionByName("탄산2+1"))
                .build();
        Product nonPromotionCoke = new Builder()
                .name("콜라")
                .price(1000)
                .quantity(5)
                .build();

        products.updateProduct(coke);
        products.updateProduct(nonPromotionCoke);

        Set<Promotion> promotionSet = new HashSet<>();
        promotionSet.add(promotions.findPromotionByName("탄산2+1"));
        List<Product> allProductsByPromotion = products.findAllProductsByPromotion(promotionSet);

        Product promotedCoke = allProductsByPromotion.stream()
                .filter(product -> "콜라".equals(product.getName()) && product.getPromotion() != null)
                .findFirst()
                .orElseThrow(() -> new AssertionError("프로모션 적용된 콜라를 찾을 수 없습니다."));
        assertThat(promotedCoke.getQuantity()).isEqualTo(5);

        Product nonPromotedCoke = allProductsByPromotion.stream()
                .filter(product -> "콜라".equals(product.getName()) && product.getPromotion() == null)
                .findFirst()
                .orElseThrow(() -> new AssertionError("프로모션 미적용된 콜라를 찾을 수 없습니다."));
        assertThat(nonPromotedCoke.getQuantity()).isEqualTo(2);
    }

}