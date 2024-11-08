package store.util;

import java.time.LocalDate;
import store.domain.Promotion;

public class PromotionConverter implements Converter<Promotion> {

    private static final int NAME_INDEX = 0;
    private static final int PURCHASE_COUNT_INDEX = 1;
    private static final int BONUS_ITEM_COUNT_INDEX = 2;
    private static final int START_DAY_INDEX = 3;
    private static final int END_DAY_INDEX = 4;

    @Override
    public String convertToString(Promotion promotion) {
        return String.join(DELIMITER,
                promotion.getName(),
                String.valueOf(promotion.getPurchaseCount()),
                String.valueOf(promotion.getBonusItemCount()),
                String.valueOf(promotion.getStartDay()),
                String.valueOf(promotion.getEndDay()));
    }

    @Override
    public Promotion convertToObject(String string) {
        String[] data = string.split(DELIMITER);
        return new Promotion.Builder()
                .name(data[NAME_INDEX])
                .purchaseCount(Integer.parseInt(data[PURCHASE_COUNT_INDEX]))
                .bonusItemCount(Integer.parseInt(data[BONUS_ITEM_COUNT_INDEX]))
                .startDay(LocalDate.parse(data[START_DAY_INDEX]))
                .endDay(LocalDate.parse(data[END_DAY_INDEX]))
                .build();
    }
}
