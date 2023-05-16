package com.example.datasetsqldispatcher.util;

import com.example.datasetsqldispatcher.dto.DatabaseModelConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseModelConfigDecoder {
    public static DatabaseModelConfig decodeString(String config) throws JsonProcessingException {
        return getObjectMapper().readValue(config, DatabaseModelConfig.class);
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
