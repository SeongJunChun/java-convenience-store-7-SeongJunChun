package store.message;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

public enum SystemMessage {

    WELCOME("안녕하세요. W편의점입니다."),
    CURRENT_PRODUCTS("현재 보유하고 있는 상품입니다."),
    PRODUCT_FORMAT("- %s %s원 %s %s"),
    HAS_NO_PRODUCTS("재고 없음"),
    ENTER_PRODUCT_AND_QUANTITY("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    NON_PROMOTION_NOTICE("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    FREE_ITEM_PROMPT("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    MEMBERSHIP_DISCOUNT_PROMPT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    ANOTHER_PRODUCT_PROMPT("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String message;

    SystemMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }

    public static String productFormat(Product product) {
        return PRODUCT_FORMAT.format(product.getName(), Receipt.formatPrice(product.getPrice()),
                quantityFormat(product.getQuantity()), promotionFormat(product.getPromotion()));
    }

    private static String promotionFormat(Promotion promotion) {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }

    private static String quantityFormat(int quantity) {
        if (quantity == 0) {
            return HAS_NO_PRODUCTS.getMessage();
        }
        return quantity + "개";
    }
}
