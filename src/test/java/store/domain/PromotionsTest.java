package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionsTest {

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

    @DisplayName("현재 진행중인 프로모션 리스트를 반환한다")
    @ParameterizedTest
    @MethodSource("dates")
    void getPromotionsTest(LocalDateTime date,int expected){
        Set<Promotion> ongoingPromotions = promotions.findAllPromotionsByDate(date);
        assertThat(ongoingPromotions.size()).isEqualTo(expected);
    }
    static Stream<Arguments> dates() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2023,12,31,1,1), 0),
                Arguments.of(LocalDateTime.of(2024,1,1,1,1),2),
                Arguments.of(LocalDateTime.of(2024,11,1,1,1),3)
        );
    }



}