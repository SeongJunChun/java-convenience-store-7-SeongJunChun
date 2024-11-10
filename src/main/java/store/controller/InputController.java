package store.controller;

import java.util.Map;
import java.util.function.Supplier;
import store.domain.Order;
import store.domain.product.AvailableProducts;
import store.util.Parser;
import store.view.InputView;

public class InputController {

    private final InputView inputView;

    public InputController(InputView inputView) {
        this.inputView = inputView;
    }

    public Order getOrder(AvailableProducts availableProducts) {
        return executeWithRetry(() -> {
            Map<String, Integer> purchaseProducts = Parser.StringToMap(inputView.readPurchaseProducts());
            return Order.createOrder(purchaseProducts, availableProducts);
        });
    }

    public boolean getIsPurchaseNonPromotionProducts(String product,int quantity) {
        return executeWithRetry(() -> Parser.StringToBoolean(inputView.readPurchaseNonPromotionProducts(product,quantity)));
    }

    public boolean getIsAddFreeProducts(String product,int quantity) {
        return executeWithRetry(() -> Parser.StringToBoolean(inputView.readAddFreeProducts(product,quantity)));
    }

    public boolean getMembershipDiscount() {
        return executeWithRetry(() -> Parser.StringToBoolean(inputView.readMembershipDiscount()));
    }

    public boolean getPurchaseAnother() {
        return executeWithRetry(() -> Parser.StringToBoolean(inputView.readPurchaseAnother()));
    }

    private <T> T executeWithRetry(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException exception) {
                inputView.printInputException(exception);
            }
        }
    }
}
