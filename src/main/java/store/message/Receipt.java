package store.message;

import java.text.DecimalFormat;
import java.util.stream.Collectors;
import store.dto.ProductSummaryDto;
import store.dto.ReceiptDto;

public enum Receipt {

    HEADER("==============W 편의점================\n"),
    ITEM_HEADER("상품명                 수량      금액\n"),
    ITEM("%s %s %s\n"),
    PROMOTION_HEADER("=============증    정===============\n"),
    PROMOTION_ITEM("%s %d\n"),
    FOOTER("====================================\n"),
    TOTAL("총구매액                %s %s\n"),
    EVENT_DISCOUNT("행사할인                       %s\n"),
    MEMBERSHIP_DISCOUNT("멤버십할인                      %s\n"),
    FINAL_AMOUNT("내실돈                         %s\n");

    private final String format;
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("#,###");

    Receipt(String format) {
        this.format = format;
    }

    public String format(Object... args) {
        return String.format(format, args);
    }

    public static String formatReceipt(ReceiptDto receiptDto) {
        return formatHeader(receiptDto) +
                formatPromotionProducts(receiptDto) +
                formatPrices(receiptDto);
    }

    private static String formatHeader(ReceiptDto receiptDto) {
        return HEADER.format() + ITEM_HEADER.format() +
                receiptDto.getProducts().stream()
                        .map(Receipt::formatProductLine)
                        .collect(Collectors.joining());
    }

    private static String formatProductLine(ProductSummaryDto product) {
        return ITEM.format(rightPadding(21, product.getName()), rightPadding(5, String.valueOf(product.getQuantity())),
                formatPrice(product.getTotalPrice()));
    }

    private static String formatPromotionProducts(ReceiptDto receiptDto) {
        return PROMOTION_HEADER.format() +
                receiptDto.getPromotionProducts().entrySet().stream()
                        .map(entry -> PROMOTION_ITEM.format(rightPadding(21, entry.getKey()), entry.getValue()))
                        .collect(Collectors.joining());
    }

    private static String formatPrices(ReceiptDto receiptDto) {
        return FOOTER.format() +
                TOTAL.format(rightPadding(5, String.valueOf(receiptDto.getTotalPriceAndQuantity().getTotalQuantity())),
                        formatPrice(receiptDto.getTotalPriceAndQuantity().getTotalPrice())) +
                EVENT_DISCOUNT.format(formatPrice(-receiptDto.getPromotionDiscount())) +
                MEMBERSHIP_DISCOUNT.format(formatPrice(-receiptDto.getMembershipDiscount())) +
                FINAL_AMOUNT.format(formatPrice(receiptDto.getTotalPayment()));
    }

    public static String formatPrice(int price) {
        return PRICE_FORMAT.format(price);
    }

    private static String rightPadding(int size, String word) {
        String formatter = String.format("%%%ds", getKorCnt(word) - size);
        return String.format(formatter, word);
    }

    private static int getKorCnt(String kor) {
        int cnt = 0;
        for (int i = 0; i < kor.length(); i++) {
            if (kor.charAt(i) >= '가' && kor.charAt(i) <= '힣') {
                cnt++;
            }
        }
        return cnt;
    }

}
