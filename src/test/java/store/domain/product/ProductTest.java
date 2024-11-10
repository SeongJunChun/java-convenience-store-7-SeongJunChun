package store.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product.Builder;

class ProductTest {

    @DisplayName("수량이 감소됨을 확인한다")
    @Test
    void reduceQuantity() {
        Product coke = new Builder()
                .name("콜라")
                .price(1000)
                .quantity(10)
                .build();
        coke.reduceQuantity(5);
        assertThat(coke.getQuantity()).isEqualTo(5);
    }
}