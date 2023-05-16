package com.example.datasetsqldispatcher.util.validator;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public class DatasetValidator {

    public static void init(MultipartFile file, String config) throws FileNotFoundException, JsonProcessingException {
        DatasetValidationProcessor datasetValidationProcessor = DatasetValidationProcessor.link(
                new FileIsNotNullValidator(),
                new BadFileTypeValidator(),
                new ConfigStructureValidator()
        );
        datasetValidationProcessor.validate(file, config);
    }

}
