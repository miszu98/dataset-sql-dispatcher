package com.example.datasetsqldispatcher.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CsvDataTypeUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", "test123", "123test", "fdgdsag"})
    void shouldRecognizeStringType(String value) {
        Optional<CsvDataTypeUtil.DataType> dataType = CsvDataTypeUtil.recognizeType(value);

        CsvDataTypeUtil.DataType result = dataType.get();

        assertEquals(CsvDataTypeUtil.DataType.STRING, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 123, 321, 10000, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void shouldRecognizeStringType(Integer value) {
        Optional<CsvDataTypeUtil.DataType> dataType = CsvDataTypeUtil.recognizeType(value);

        CsvDataTypeUtil.DataType result = dataType.get();

        assertEquals(CsvDataTypeUtil.DataType.NUMBER, result);
    }

}
