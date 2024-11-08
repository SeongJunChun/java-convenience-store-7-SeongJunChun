package store.domain;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int purchaseCount;
    private final int bonusItemCount;
    private final LocalDate startDay;
    private final LocalDate endDay;

    public String getName() {
        return name;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public int getBonusItemCount() {
        return bonusItemCount;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public static class Builder {
        private String name;
        private int purchaseCount;
        private int bonusItemCount;
        private LocalDate startDay;
        private LocalDate endDay;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder purchaseCount(int purchaseCount) {
            this.purchaseCount = purchaseCount;
            return this;
        }

        public Builder bonusItemCount(int bonusItemCount) {
            this.bonusItemCount = bonusItemCount;
            return this;
        }

        public Builder startDay(LocalDate startDay) {
            this.startDay = startDay;
            return this;
        }

        public Builder endDay(LocalDate endDay) {
            this.endDay = endDay;
            return this;
        }

        public Promotion build() {
            return new Promotion(this);
        }
    }

    private Promotion(Builder builder) {
        this.name = builder.name;
        this.purchaseCount = builder.purchaseCount;
        this.bonusItemCount = builder.bonusItemCount;
        this.startDay = builder.startDay;
        this.endDay = builder.endDay;
    }

}
