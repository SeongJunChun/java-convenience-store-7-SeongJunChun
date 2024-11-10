package store.view;

import static store.message.SystemMessage.ANOTHER_PRODUCT_PROMPT;
import static store.message.SystemMessage.ENTER_PRODUCT_AND_QUANTITY;
import static store.message.SystemMessage.FREE_ITEM_PROMPT;
import static store.message.SystemMessage.MEMBERSHIP_DISCOUNT_PROMPT;
import static store.message.SystemMessage.NON_PROMOTION_NOTICE;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readPurchaseProducts() {
        return readLine(ENTER_PRODUCT_AND_QUANTITY.getMessage());
    }

    public String readPurchaseNonPromotionProducts(String product,int quantity) {
        return readLine(NON_PROMOTION_NOTICE.format(product,quantity));
    }

    public String readAddFreeProducts(String product,int quantity) {
        return readLine(FREE_ITEM_PROMPT.format(product,quantity));
    }

    public String readMembershipDiscount() {
        return readLine(MEMBERSHIP_DISCOUNT_PROMPT.getMessage());
    }

    public String readPurchaseAnother() {
        return readLine(ANOTHER_PRODUCT_PROMPT.getMessage());
    }

    public void printInputException(Exception exception) {
        System.out.println(exception.getMessage());
    }

    private String readLine(String message) {
        System.out.println(message);
        String input = Console.readLine();
        System.out.print(System.lineSeparator());
        return input;
    }

}
