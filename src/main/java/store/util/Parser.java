package store.util;

import static store.message.ErrorMessage.DUPLICATE_PRODUCT_NAME;
import static store.message.ErrorMessage.EMPTY_OR_BLANK;
import static store.message.ErrorMessage.INVALID_FORMAT;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parser {
    private static final String INTEGER_REGEX = "-?\\d+";
    private static final String NAME_REGEX = "^[a-zA-Z가-힣0-9\\s]+$";

    private static final String TRUE = "Y";
    private static final String FALSE = "N";

    private static final String DELIMITER = ",";
    private static final String MAP_DELIMITER = "-";
    private static final int PROPERTY_COUNT = 2;

    public static int StringToInt(String str) {
        validateBlank(str);
        validateIntegerFormat(str);
        return Integer.parseInt(str);
    }

    public static boolean StringToBoolean(String str) {
        validateBlank(str);
        validateBooleanFormat(str);
        return str.equals(TRUE);
    }

    public static Map<String, Integer> StringToMap(String str) {
        validateBlank(str);
        return Arrays.stream(str.split(DELIMITER))
                .map(Parser::parseItem)
                .peek(parts -> validatePropertyCount(parts.length))
                .collect(Collectors.toMap(
                        parts -> validateProductName(parts[0]),
                        parts -> StringToInt(parts[1]),
                        Parser::validateDuplicate
                ));
    }

    private static void validatePropertyCount(int count) {
        if (count != PROPERTY_COUNT) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessageWithRetry());
        }
    }

    private static String[] parseItem(String item) {
        validateBlank(item);
        validateMapFormat(item);
        String content = item.substring(1, item.length() - 1);
        return content.split(MAP_DELIMITER);
    }

    private static String validateProductName(String productName) {
        validateBlank(productName);
        if (!productName.matches(NAME_REGEX)) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessageWithRetry());
        }
        return productName;
    }

    private static Integer validateDuplicate(Integer existing, Integer replacement) {
        throw new IllegalArgumentException(DUPLICATE_PRODUCT_NAME.getMessageWithRetry());
    }

    private static void validateIntegerFormat(String input) {
        if (!input.matches(INTEGER_REGEX)) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessageWithRetry());
        }
    }

    private static void validateBooleanFormat(String input) {
        if (!(Objects.equals(input, TRUE) || Objects.equals(input, FALSE))) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessageWithRetry());
        }
    }

    private static void validateMapFormat(String input) {
        validateBlank(input);
        if (!(input.startsWith("[") && input.endsWith("]") && input.contains(MAP_DELIMITER))) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessageWithRetry());
        }
    }

    private static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(EMPTY_OR_BLANK.getMessageWithRetry());
        }
    }
}
