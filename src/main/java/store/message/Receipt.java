package store.message;

import java.text.DecimalFormat;
import java.util.stream.Collectors;
import store.dto.ProductSummaryDto;
import store.dto.ReceiptDto;

public enum Receipt {

    HEADER("==============W 편의점================\n"),
    ITEM_HEADER("상품명                수량      금액\n"),
    ITEM("%-11s %10d %9s\n"), // 상품명 15칸 왼쪽 정렬, 수량 8칸 오른쪽 정렬, 금액 12칸 오른쪽 정렬
    PROMOTION_HEADER("=============증    정===============\n"),
    PROMOTION_ITEM("%-15s %8d\n"), // 상품명 15칸 왼쪽 정렬, 수량 8칸 오른쪽 정렬
    FOOTER("====================================\n"),
    TOTAL("총구매액         %8d %10s\n"), // 수량 8칸, 금액 12칸 오른쪽 정렬
    EVENT_DISCOUNT("행사할인                 %11s\n"), // 할인 금액 12칸 오른쪽 정렬
    MEMBERSHIP_DISCOUNT("멤버십할인              %12s\n"), // 할인 금액 12칸 오른쪽 정렬
    FINAL_AMOUNT("내실돈                 %12s\n");

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
        return ITEM.format(product.getName(), product.getQuantity(), formatPrice(product.getTotalPrice()));
    }

    private static String formatPromotionProducts(ReceiptDto receiptDto) {
        return PROMOTION_HEADER.format() +
                receiptDto.getPromotionProducts().entrySet().stream()
                        .map(entry -> PROMOTION_ITEM.format(entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining());
    }

    private static String formatPrices(ReceiptDto receiptDto) {
        return FOOTER.format() +
                TOTAL.format(receiptDto.getTotalPriceAndQuantity().getTotalQuantity(),
                        formatPrice(receiptDto.getTotalPriceAndQuantity().getTotalPrice())) +
                EVENT_DISCOUNT.format(formatPrice(-receiptDto.getPromotionDiscount())) +
                MEMBERSHIP_DISCOUNT.format(formatPrice(-receiptDto.getMembershipDiscount())) +
                FINAL_AMOUNT.format(formatPrice(receiptDto.getTotalPayment()));
    }

    private static String formatPrice(int price) {
        return PRICE_FORMAT.format(price);
    }

}
