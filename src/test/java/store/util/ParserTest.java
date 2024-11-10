package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParserTest {

    @DisplayName("문자열을 정수로 변환한다")
    @Test
    void StringToIntegerTest(){
        assertThat(Parser.StringToInt("14")).isEqualTo(14);
    }

    @DisplayName("문자열을 Map으로 변환한다")
    @Test
    void StringToMapTest(){
        Map<String, Integer> expectedmap = new HashMap<String, Integer>();
        expectedmap.put("콜라",3);
        expectedmap.put("사이다",4);
        assertThat(Parser.StringToMap("[콜라-3],[사이다-4]")).isEqualTo(expectedmap);

    }

    @DisplayName("문자열을 boolean으로 변환한다")
    @Test
    void StringToBooleanTest(){
        assertThat(Parser.StringToBoolean("Y")).isTrue();
    }

    @DisplayName("잘못된 문자열을 정수 변환시 예외를 발생 시킨다")
    @ParameterizedTest
    @MethodSource("wrongNumbers")
    void validateStringToIntegerTest(String input){
        assertThatThrownBy(() ->Parser.StringToInt(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
    static Stream<Arguments> wrongNumbers() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("three"),
                Arguments.of("10d"),
                Arguments.of("1 0"),
                Arguments.of("10.5")
        );
    }

    @DisplayName("잘못된 문자열을 Map 변환시 예외를 발생 시킨다")
    @ParameterizedTest
    @MethodSource("wrongInputs")
    void validateStringToMapTest(String input){
        assertThatThrownBy(() ->Parser.StringToMap(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
    static Stream<Arguments> wrongInputs() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("[콜라,3]"),
                Arguments.of("콜라-3,[사이다-2]"),
                Arguments.of("[콜라-3]]"),
                Arguments.of("[],[콜라-4]"),
                Arguments.of("[사이다-1][콜라-4]"),
                Arguments.of("[사이다-1],[콜라-4개]"),
                Arguments.of("[콜라-1],[콜라-4]")
        );
    }

    @DisplayName("잘못된 문자열을 Boolean 변환시 예외를 발생 시킨다")
    @ParameterizedTest
    @MethodSource("wrongBooleans")
    void validateStringToBooleanTest(String input){
        assertThatThrownBy(() ->Parser.StringToBoolean(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
    static Stream<Arguments> wrongBooleans() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("y"),
                Arguments.of("n"),
                Arguments.of("1"),
                Arguments.of("0"),
                Arguments.of("true"),
                Arguments.of("Yes")
        );
    }

}