package store.dto;

import java.util.List;
import java.util.Map;
import store.domain.Membership;
import store.domain.OrderResult;

public class ReceiptDto {
    private final List<ProductSummaryDto> products;
    private final Map<String, Integer> promotionProducts;
    private final OrderResultDto totalPriceAndQuantity;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int totalPayment;

    public ReceiptDto(OrderResult result, Membership membership) {
        this.products = result.getProductSummary();
        this.promotionProducts = result.getPromotionSummary();
        this.totalPriceAndQuantity = result.calculateTotalPriceAndQuantity();
        this.promotionDiscount = result.calculatePromotionDiscount();
        this.membershipDiscount = result.calculateMembershipDiscount(membership);
        this.totalPayment = result.calculatePayment(membership);
    }

    public List<ProductSummaryDto> getProducts() {
        return products;
    }

    public Map<String, Integer> getPromotionProducts() {
        return promotionProducts;
    }

    public OrderResultDto getTotalPriceAndQuantity() {
        return totalPriceAndQuantity;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getTotalPayment() {
        return totalPayment;
    }
}
