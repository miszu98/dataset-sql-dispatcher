package com.example.datasetsqldispatcher.util.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import static java.util.Objects.nonNull;

public abstract class DatasetValidationProcessor {

    public DatasetValidationProcessor nextProcessor;

    public static DatasetValidationProcessor link(DatasetValidationProcessor first,
                                                  DatasetValidationProcessor... chain) {
        DatasetValidationProcessor head = first;
        for (DatasetValidationProcessor nextInChain : chain) {
            head.nextProcessor = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract void validate(MultipartFile file, String config) throws FileNotFoundException, JsonProcessingException;

    protected void validateNext(MultipartFile file, String config) throws FileNotFoundException, JsonProcessingException {
        if (nonNull(nextProcessor)) {
            nextProcessor.validate(file, config);
        }
    }
}
