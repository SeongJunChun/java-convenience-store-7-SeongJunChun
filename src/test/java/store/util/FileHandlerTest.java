package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;

class FileHandlerTest {

    private static final String READ_FILE_PATH = "src/test/resources/test.md";
    private static final String WRITE_FILE_PATH = "src/test/resources/writeTest.md";

    @DisplayName("파일을 성공적으로 읽어 온다")
    @Test
    void loadFromFile() throws IOException {
        FileHandler<Product> fileHandler = new FileHandler<>(new ProductConverter());
        List<Product> products = fileHandler.loadFromFile(READ_FILE_PATH);
        assertThat(products.size()).isEqualTo(16);
    }

    @DisplayName("파일 성공적으로 작성한다")
    @Test
    void saveToFile() throws IOException {
        FileHandler<Product> fileHandler = new FileHandler<>(new ProductConverter());
        File file = new File(WRITE_FILE_PATH);
        Product product = new Product.Builder()
                .name("오렌지 에이드")
                .price(1500)
                .quantity(9)
                .promotion(null)
                .build();
        fileHandler.saveToFile(WRITE_FILE_PATH,List.of(product));
        assertThat(file.exists()).isTrue();
        if(file.exists()) {
            file.delete();
        }
    }

}