package store.util;

import static store.message.ErrorMessage.FILE_LOAD_ERROR;
import static store.message.ErrorMessage.FILE_SAVE_ERROR;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler<T> {

    private static final int LINE_HEADER = 1;
    private static final String DELIMITER = ",";

    private final Converter<T> converter;

    public FileHandler(final Converter<T> converter) {
        this.converter = converter;
    }

    public void saveToFile(String filename, List<T> items) {
        try {
            Path path = Paths.get(filename);
            List<String> newLines = updateFileContents(items);
            Files.write(path, newLines, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new IllegalStateException(FILE_SAVE_ERROR.getMessage());
        }
    }


    public List<T> loadFromFile(String filename) {
        Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .skip(LINE_HEADER)
                    .map(converter::convertToObject)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(FILE_LOAD_ERROR.getMessage());
        }
    }

    private List<String> updateFileContents(List<T> items) throws IOException {
        List<String> newLines = items.stream()
                .map(converter::convertToString)
                .collect(Collectors.toList());
        String header = getHeaderFromFields(items);
        newLines.addFirst(header);
        return newLines;
    }

    private String getHeaderFromFields(List<T> items) {
        Field[] fields = items.getFirst().getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .map(field->toSnakeCase(field.getName()))
                .collect(Collectors.joining(DELIMITER));
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("(?<!^)([A-Z])", "_$1").toLowerCase();
    }

}
