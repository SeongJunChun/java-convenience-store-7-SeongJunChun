package store.view;

import static store.message.SystemMessage.CURRENT_PRODUCTS;
import static store.message.SystemMessage.WELCOME;

import java.util.List;
import store.domain.product.Product;
import store.dto.ReceiptDto;
import store.message.Receipt;

public class OutputView {

    public void printCurrentProducts(List<Product> products) {
        System.out.println(WELCOME.getMessage());
        System.out.println(CURRENT_PRODUCTS.getMessage());
        products.forEach(System.out::println);
    }

    public void printReceipt(ReceiptDto receipt) {
        System.out.println(Receipt.formatReceipt(receipt));
    }
}
