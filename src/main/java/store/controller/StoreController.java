package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.Set;
import store.domain.Membership;
import store.domain.Order;
import store.domain.product.AvailableProducts;
import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.dto.ReceiptDto;
import store.service.OrderService;
import store.util.FileHandler;
import store.util.ProductConverter;
import store.util.PromotionConverter;
import store.view.OutputView;

public class StoreController {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    private final InputController inputController;
    private final OutputView outputView;

    public StoreController(InputController inputController, OutputView outputView) {
        this.inputController = inputController;
        this.outputView = outputView;
    }

    public void run() {
        Promotions promotions = loadPromotions();
        Products products = loadProducts(promotions);
        do {
            executePurchaseCycle(promotions, products);
        } while (inputController.getPurchaseAnother());
    }

    private void executePurchaseCycle(Promotions promotions, Products products) {
        List<Product> currentProducts = getCurrentProducts(promotions, products);
        AvailableProducts availableProducts = AvailableProducts.create(currentProducts);

        OrderService orderService = processOrder(availableProducts);
        createReceipt(orderService);
    }

    private List<Product> getCurrentProducts(Promotions promotions, Products products) {
        Set<Promotion> allPromotionsByDate = promotions.findAllPromotionsByDate(DateTimes.now());
        List<Product> currentProducts = products.findAllProductsByPromotion(allPromotionsByDate);
        outputView.printCurrentProducts(currentProducts);
        return currentProducts;
    }

    private OrderService processOrder(AvailableProducts availableProducts) {
        Order order = inputController.getOrder(availableProducts);
        OrderService orderService = new OrderService(availableProducts);
        PurchaseController purchaseController = new PurchaseController(inputController, orderService);

        order.getOrders().forEach((product, quantity) ->
                purchaseController.purchaseProduct(product, quantity, availableProducts)
        );
        return orderService;
    }

    private void createReceipt(OrderService orderService) {
        if (!orderService.getOrderResult().hasNoPurchases()) {
            Membership membership = new Membership(inputController.getMembershipDiscount());
            ReceiptDto receiptDto = new ReceiptDto(orderService.getOrderResult(), membership);
            outputView.printReceipt(receiptDto);
        }
    }


    private Promotions loadPromotions() {
        FileHandler<Promotion> fileHandler = new FileHandler<>(new PromotionConverter());
        List<Promotion> promotions = fileHandler.loadFromFile(PROMOTION_FILE_PATH);
        return new Promotions(promotions);
    }


    private Products loadProducts(Promotions promotions) {
        FileHandler<Product> fileHandler = new FileHandler<>(new ProductConverter(promotions));
        List<Product> products = fileHandler.loadFromFile(PRODUCT_FILE_PATH);
        return Products.getInstance(products);
    }
}
