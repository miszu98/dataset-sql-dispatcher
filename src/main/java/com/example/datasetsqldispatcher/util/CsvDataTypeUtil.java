package com.example.datasetsqldispatcher.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

@UtilityClass
public class CsvDataTypeUtil {
    protected enum DataType {
        DATE("dd/dd/ddd"),
        STRING("^[A-Za-z0-9? ,_-]+"),
        NUMBER("[0-9]");

        private String regex;

        DataType(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }

    public static Optional<DataType> recognizeType(Object value) {
        return Arrays.stream(DataType.values())
                .filter(dt -> Pattern.compile(dt.getRegex()).matcher((String) value).matches())
                .findFirst();
    }

}
