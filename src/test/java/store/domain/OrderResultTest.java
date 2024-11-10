package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.dto.ProductSummaryDto;
import store.domain.product.Product;

class OrderResultTest extends PromotionTestBase{
    OrderResult orderResult;
    @BeforeEach
    void setUp() {
        orderResult = new OrderResult();
        Product coke = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(7)
                .promotion(promotions.findPromotionByName("탄산2+1"))
                .build();
        Product nonPromotionCoke = new Product.Builder()
                .name("콜라")
                .price(1000)
                .quantity(3)
                .promotion(null)
                .build();
        Product orange = new Product.Builder()
                .name("오렌지 주스")
                .price(2000)
                .quantity(3)
                .promotion(promotions.findPromotionByName("MD추천상품"))
                .build();
        Product potato = new Product.Builder()
                .name("감자칩")
                .price(3000)
                .quantity(5)
                .promotion(null)
                .build();
        List<Product> products = List.of(coke, nonPromotionCoke, orange, potato);
        products.forEach(product -> {orderResult.addProduct(product);});
    }

    @DisplayName("모든 구매 물품과 수량을 확인한다")
    @Test
    void productSummaryTest(){
        List<ProductSummaryDto> expectedSummary = List.of(
                new ProductSummaryDto("콜라", 10, 10000),
                new ProductSummaryDto("오렌지 주스", 3, 6000),
                new ProductSummaryDto("감자칩", 5, 15000)
        );
        assertThat(orderResult.getProductSummary())
                .containsExactlyInAnyOrderElementsOf(expectedSummary);
    }

    @DisplayName("모든 증정품의 이름과 수량을 확인한다")
    @Test
    void getPromotionSummaryTest(){
        Map<String, Integer> expectedSummary = new HashMap<>();
        expectedSummary.put("콜라", 2);
        expectedSummary.put("오렌지 주스", 1);
        assertThat(orderResult.getPromotionSummary()).isEqualTo(expectedSummary);
    }

    @DisplayName("증정 품으로 할인받은 금액을 확인한다")
    @Test
    void calculatePromotionDiscountTest(){
        assertThat(orderResult.calculatePromotionDiscount()).isEqualTo(4000);
    }


    @DisplayName("할인 적용전 총 금액을 확인한다")
    @Test
    void calculateTotalPriceTest(){
        assertThat(orderResult.calculateTotalPriceAndQuantity().getTotalPrice()).isEqualTo(31000);
    }

    @DisplayName("구매한 상품의 갯수를 확인한다")
    @Test
    void calculateTotalQuantityTest(){
        assertThat(orderResult.calculateTotalPriceAndQuantity().getTotalQuantity()).isEqualTo(18);
    }

    @DisplayName("총 프로모션 적용 금액을 확인한다")
    @Test
    void calculateTotalPromotionPriceTest(){
        assertThat(orderResult.calculateTotalPromotionPrice()).isEqualTo(10000);
    }

    @DisplayName("멤버쉽 적용시 할인받는 금액을 확인한다")
    @Test
    void calculateMembershipDiscount(){
        Membership membership = new Membership(true);
        assertThat(orderResult.calculateMembershipDiscount(membership)).isEqualTo(6300);
    }

    @DisplayName("멤버쉽 적용시 최종 결제 금액을 확인한다")
    @Test
    void calculatePayment(){
        Membership membership = new Membership(true);
        assertThat(orderResult.calculatePayment(membership)).isEqualTo(20700);
    }

    @DisplayName("멤버쉽 미 적용시 최종 결제 금액을 확인한다")
    @Test
    void calculatePaymentNonMembership(){
        Membership membership = new Membership(false);
        assertThat(orderResult.calculatePayment(membership)).isEqualTo(27000);
    }



}