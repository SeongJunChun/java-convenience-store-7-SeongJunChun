package store.util;

public interface Converter<T> {

    String DELIMITER = ",";

    String convertToString(T object);

    T convertToObject(String string);

}
