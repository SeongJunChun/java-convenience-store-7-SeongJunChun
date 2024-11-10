package store.controller;

import store.domain.product.AvailableProducts;
import store.domain.product.Product;
import store.service.OrderService;

public class PurchaseController {

    private final InputController inputController;
    private final OrderService orderService;

    public PurchaseController(InputController inputController, OrderService orderService) {
        this.inputController = inputController;
        this.orderService = orderService;
    }

    public void purchaseProduct(String product, int amount, AvailableProducts availableProducts) {
        Product promotionProduct = availableProducts.findPromotionProduct(product);

        if (promotionProduct == null) {
            orderService.buyNonPromotionProduct(product, amount);
            return;
        }

        handlePromotionProductPurchase(product, amount, promotionProduct);
    }

    private void handlePromotionProductPurchase(String product, int amount, Product promotionProduct) {
        int threshold = promotionProduct.getPromotion().getPromotionThreshold();
        int promotionRemaining = amount % threshold;
        int availableQuantity = promotionProduct.getQuantity();

        if (availableQuantity >= amount) {
            processFullPromotion(product, amount, promotionRemaining, promotionProduct);
        } else {
            processPartialPromotion(product, amount, promotionRemaining, availableQuantity);
        }
    }

    private void processFullPromotion(String product, int amount, int promotionRemaining, Product promotionProduct) {
        if (promotionRemaining == 0) {
            orderService.buyPromotionProduct(product, amount);
            return;
        }
        if (isBonusApplicable(promotionRemaining, promotionProduct, amount)) {
            processBonusOption(product, amount, promotionProduct);
            return;
        }
        handleNonPromotionPurchase(product, amount, promotionRemaining);
    }

    private void processPartialPromotion(String product, int amount, int promotionRemaining, int availableQuantity) {
        int remaining = calculateRemainingAmount(amount, availableQuantity, promotionRemaining);
        if (inputController.getIsPurchaseNonPromotionProducts(product, remaining)) {
            executeMixedPurchase(product, amount, availableQuantity);
            return;
        }
        orderService.buyPromotionProduct(product, amount - remaining);
    }

    private void handleNonPromotionPurchase(String product, int amount, int promotionRemaining) {
        if (inputController.getIsPurchaseNonPromotionProducts(product, promotionRemaining)) {
            orderService.buyPromotionProduct(product, amount);
            return;
        }
        orderService.buyPromotionProduct(product, amount - promotionRemaining);
    }

    private int calculateRemainingAmount(int amount, int availableQuantity, int promotionRemaining) {
        return amount - availableQuantity + promotionRemaining;
    }

    private boolean isBonusApplicable(int promotionRemaining, Product promotionProduct, int amount) {
        return promotionRemaining == promotionProduct.getPromotion().getRequiredPurchaseAmount() &&
                canAddBonusProduct(promotionProduct, amount);
    }

    private boolean canAddBonusProduct(Product promotionProduct, int amount) {
        return promotionProduct.getPromotion().getBonusAmount() + amount <= promotionProduct.getQuantity();
    }

    private void processBonusOption(String product, int amount, Product promotionProduct) {
        int bonusAmount = promotionProduct.getPromotion().getBonusAmount();
        if (inputController.getIsAddFreeProducts(product, bonusAmount)) {
            orderService.buyPromotionProduct(product, bonusAmount + amount);
            return;
        }
        orderService.buyPromotionProduct(product, amount);
    }

    private void executeMixedPurchase(String product, int amount, int availableQuantity) {
        orderService.buyPromotionProduct(product, availableQuantity);
        orderService.buyNonPromotionProduct(product, amount - availableQuantity);
    }

}
