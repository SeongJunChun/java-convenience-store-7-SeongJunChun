package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

class PromotionConverterTest {
    @DisplayName("문자열을 Promotion 객체로 변환한다")
    @Test
    void convertToPromotion() {
        PromotionConverter promotionConverter = new PromotionConverter();
        Promotion promotion =promotionConverter.convertToObject("MD추천상품,1,1,2024-01-01,2024-12-31");
        assertThat(promotion.getName()).isEqualTo("MD추천상품");
    }

    @DisplayName("Promotion 객체를 문자열로 변환한다")
    @Test
    void convertToString() {
        PromotionConverter promotionConverter = new PromotionConverter();
        Promotion promotion = new Promotion.Builder()
                .name("MD추천상품")
                .buy(1)
                .get(1)
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,12,31))
                .build();
        String line = promotionConverter.convertToString(promotion);
        assertThat(line).isEqualTo("MD추천상품,1,1,2024-01-01,2024-12-31");
    }

}