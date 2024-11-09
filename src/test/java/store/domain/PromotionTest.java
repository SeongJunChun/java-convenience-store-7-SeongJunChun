package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    Promotion promotion;

    @BeforeEach
    void setUp()  {
        this.promotion = new Promotion.Builder()
                .name("탄산2+1")
                .buy(2)
                .get(1)
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,12,31))
                .build();
    }

    @DisplayName("해당 프로 모션이 진행중일경우 true를 반환한다")
    @Test
    void OngoingPromotionTest() {
        LocalDateTime date = LocalDateTime.of(2024,11,1,12,0);
        assertThat(promotion.isOngoingPromotion(date)).isTrue();
    }

    @DisplayName("해당 프로 모션 기간이 아니면 false를 반환한다")
    @Test
    void NotOngoingPromotionTest() {
        LocalDateTime date = LocalDateTime.of(2023,1,1,12,0);
        assertThat(promotion.isOngoingPromotion(date)).isFalse();
    }

}