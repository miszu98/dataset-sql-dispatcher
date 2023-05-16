package com.example.datasetsqldispatcher.util.validator;

import com.example.datasetsqldispatcher.dto.DatabaseModelConfig;
import com.example.datasetsqldispatcher.dto.DatabaseSetup;
import com.example.datasetsqldispatcher.util.DatabaseModelConfigDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Objects.nonNull;

public class ConfigStructureValidator extends DatasetValidationProcessor {
    @Override
    public void validate(MultipartFile file, String config)
            throws JsonProcessingException {
        boolean configIsNotNull = nonNull(config);
        if (configIsNotNull) {
            DatabaseModelConfig decodedConfig = DatabaseModelConfigDecoder.decodeString(config);
            List<DatabaseSetup> databaseSetups = decodedConfig.getConfigs();
            // todo validate all configs part
        }
    }

}
