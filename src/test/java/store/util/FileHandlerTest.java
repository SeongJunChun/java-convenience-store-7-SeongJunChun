package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

class FileHandlerTest {

    private static final String READ_FILE_PATH = "src/test/resources/promotions_test.md";
    private static final String WRITE_FILE_PATH = "src/test/resources/writeTest.md";

    @DisplayName("파일을 성공적으로 읽어 온다")
    @Test
    void loadFromFile() throws IOException {
        FileHandler<Promotion> fileHandler = new FileHandler<>(new PromotionConverter());
        List<Promotion> promotions = fileHandler.loadFromFile(READ_FILE_PATH);
        assertThat(promotions.size()).isEqualTo(3);
    }

    @DisplayName("파일 성공적으로 작성한다")
    @Test
    void saveToFile() throws IOException {
        FileHandler<Promotion> fileHandler = new FileHandler<>(new PromotionConverter());
        File file = new File(WRITE_FILE_PATH);
        Promotion promotion = new Promotion.Builder()
                .name("2+2")
                .buy(2)
                .get(2)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .build();
        fileHandler.saveToFile(WRITE_FILE_PATH, List.of(promotion));
        assertThat(file.exists()).isTrue();
        if (file.exists()) {
            file.delete();
        }
    }

}