package store.domain;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

public abstract class PromotionTestBase {
    protected Promotions promotions;

    @BeforeEach
    void setUpPromotions() {
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
        List<Promotion> promotionsList = List.of(soda, md, special);
        this.promotions = new Promotions(promotionsList);
    }
}
