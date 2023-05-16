package com.example.datasetsqldispatcher.util;

import com.example.datasetsqldispatcher.dto.DatabaseModelConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static com.example.datasetsqldispatcher.util.MockDataProvider.getDatabaseConfigJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseModelConfigDecoderTest {

    @Test
    void shouldDecodeStringToDatabaseModelConfig() throws JsonProcessingException {
        final String json = getDatabaseConfigJson();

        DatabaseModelConfig config = DatabaseModelConfigDecoder.decodeString(json);

        assertEquals(2, config.getConfigs().size());
    }


}
