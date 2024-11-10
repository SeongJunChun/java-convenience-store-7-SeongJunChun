package store.message;

public enum ErrorMessage {

    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다."),
    NON_EXISTENT_PRODUCT("존재하지 않는 상품입니다."),
    EXCEEDS_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다."),
    QUANTITY_MUST_BE_ONE_OR_MORE("한 개 이상의 수량을 입력해야 합니다."),
    DUPLICATE_PRODUCT_NAME("상품의 이름은 중복될 수 없습니다."),
    INVALID_INPUT("잘못된 입력입니다."),
    PLEASE_TRY_AGAIN("다시 입력해 주세요."),

    STOCK_MUST_BE_NON_NEGATIVE("상품 제고는 음수가 될 수 없습니다"),
    NAME_CANNOT_BE_EMPTY("상품의 이름은 비어있을 수 없습니다."),
    PRICE_MUST_BE_NON_NEGATIVE("상품의 가격은 음수가 될 수 없습니다"),
    PRODUCT_NOT_FOUND("해당 상품을 찾을 수 없습니다."),

    FILE_SAVE_ERROR("파일 저장 중 오류가 발생했습니다."),
    FILE_LOAD_ERROR("파일 불러오기 중 오류가 발생했습니다.");

    private final static String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageWithRetry() {
        return message + " " + PLEASE_TRY_AGAIN.getMessage();
    }
}
