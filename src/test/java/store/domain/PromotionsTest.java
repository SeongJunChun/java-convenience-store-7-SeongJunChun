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

class PromotionsTest extends PromotionTestBase{

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