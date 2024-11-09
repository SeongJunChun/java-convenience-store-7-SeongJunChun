package store.util;

import java.time.LocalDate;
import store.domain.Promotion;

public class PromotionConverter implements Converter<Promotion> {

    private static final int NAME_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final int GET_INDEX = 2;
    private static final int START_DAY_INDEX = 3;
    private static final int END_DAY_INDEX = 4;

    @Override
    public String convertToString(Promotion promotion) {
        return String.join(DELIMITER,
                promotion.getName(),
                String.valueOf(promotion.getBuy()),
                String.valueOf(promotion.getGet()),
                String.valueOf(promotion.getstartDate()),
                String.valueOf(promotion.getendDate()));
    }

    @Override
    public Promotion convertToObject(String string) {
        String[] data = string.split(DELIMITER);
        return new Promotion.Builder()
                .name(data[NAME_INDEX])
                .buy(Integer.parseInt(data[BUY_INDEX]))
                .get(Integer.parseInt(data[GET_INDEX]))
                .startDate(LocalDate.parse(data[START_DAY_INDEX]))
                .endDate(LocalDate.parse(data[END_DAY_INDEX]))
                .build();
    }
}
