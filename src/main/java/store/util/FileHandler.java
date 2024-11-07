package store.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler<T> {

    private static final int LINE_HEADER = 1;

    private final Converter<T> converter;

    public FileHandler(final Converter<T> converter) {
        this.converter = converter;
    }

    public void saveToFile(String filename, List<T> items) {
        try {
            Path path = Paths.get(filename);
            List<String> newLines = updateFileContents(path, items);
            Files.write(path, newLines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException();
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
            throw new IllegalStateException();
        }
    }

    private List<String> updateFileContents(Path path, List<T> items) throws IOException {
        List<String> existingLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> newLines = items.stream()
                .map(converter::convertToString)
                .collect(Collectors.toList());
        if (!existingLines.isEmpty()) {
            newLines.addFirst(existingLines.getFirst());
        }
        return newLines;
    }

}
