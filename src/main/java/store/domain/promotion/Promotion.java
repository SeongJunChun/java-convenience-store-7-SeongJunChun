package store.domain.promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public boolean isOngoingPromotion(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        return (localDate.isEqual(startDate) || localDate.isAfter(startDate)) &&
                (localDate.isEqual(endDate) || localDate.isBefore(endDate));
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public static class Builder {
        private String name;
        private int buy;
        private int get;
        private LocalDate startDate;
        private LocalDate endDate;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder buy(int buy) {
            this.buy = buy;
            return this;
        }

        public Builder get(int get) {
            this.get = get;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Promotion build() {
            return new Promotion(this);
        }
    }

    private Promotion(Builder builder) {
        this.name = builder.name;
        this.buy = builder.buy;
        this.get = builder.get;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

}