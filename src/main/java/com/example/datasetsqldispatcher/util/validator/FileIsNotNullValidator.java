package com.example.datasetsqldispatcher.util.validator;

import com.example.datasetsqldispatcher.exception.GlobalExceptionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import static java.util.Objects.isNull;

public class FileIsNotNullValidator extends DatasetValidationProcessor {
    @Override
    public void validate(MultipartFile file, String config) throws FileNotFoundException, JsonProcessingException {
        if (isNull(file)) {
            throw new FileNotFoundException(GlobalExceptionEnum.FILE_NOT_FOUND.name());
        }
        validateNext(file, config);
    }
}
