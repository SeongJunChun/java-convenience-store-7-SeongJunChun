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
        System.out.print(System.lineSeparator());
        products.forEach(System.out::println);
        System.out.print(System.lineSeparator());
    }

    public void printReceipt(ReceiptDto receipt) {
        System.out.println(Receipt.formatReceipt(receipt));
    }
}
