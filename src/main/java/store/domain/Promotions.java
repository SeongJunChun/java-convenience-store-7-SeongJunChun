package store.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Promotions {

    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = Collections.unmodifiableList(new ArrayList<>(promotions));
    }

    public Set<Promotion> findAllPromotionsByDate(LocalDateTime date) {
        return promotions.stream()
                .filter(promotion -> promotion.isOngoingPromotion(date))
                .collect(Collectors.toSet());
    }

    public Promotion findPromotionByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}
