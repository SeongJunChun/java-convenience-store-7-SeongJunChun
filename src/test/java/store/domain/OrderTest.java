package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.AvailableProducts;
import store.domain.product.Product;
import store.domain.promotion.PromotionTestBase;


class OrderTest extends PromotionTestBase {

    private AvailableProducts products;

    @BeforeEach
    void setUp(){
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
        products = AvailableProducts.create(List.of(coke, cokePro, orange, orangePro, potato));
    }

    @DisplayName("주문한 상품이 존재하지 않을 경우 예외를 발생시킨다")
    @Test
    void validateExist(){
        Map<String,Integer> order = new HashMap<>();
        order.put("사이다",5);
        order.put("콜라",2);
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(order,products));
    }

    @DisplayName("주문한 상품의 수량이 음수일 경우 예외를 발생시킨다")
    @Test
    void validatePositive(){
        Map<String,Integer> order = new HashMap<>();
        order.put("오렌지 주스",1);
        order.put("콜라",-2);
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(order,products));
    }

    @DisplayName("주문한 상품의 갯수가 보유량 보다 많을 경우 예외를 발생시킨다")
    @Test
    void validateExcess(){
        Map<String,Integer> order = new HashMap<>();
        order.put("오렌지 주스",5);
        order.put("콜라",2);
        order.put("감자칩",6);
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(order,products));
    }
    @DisplayName("주문에 성공함을 확인 한다")
    @Test
    void orderSuccessTest(){
        Map<String,Integer> order = new HashMap<>();
        order.put("오렌지 주스",13);
        order.put("콜라",10);
        order.put("감자칩",3);
        Order newOrder = Order.createOrder(order, products);
        assertThat(newOrder).isNotNull();
    }
}