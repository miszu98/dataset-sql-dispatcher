package com.example.datasetsqldispatcher.util.validator;

import com.example.datasetsqldispatcher.exception.BadFileTypeException;
import com.example.datasetsqldispatcher.exception.GlobalExceptionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public class BadFileTypeValidator extends DatasetValidationProcessor {
    @Override
    public void validate(MultipartFile file, String config) throws FileNotFoundException, JsonProcessingException {
        final String extension = Files.getFileExtension(file.getOriginalFilename());
        boolean isNotCsvExtension = !"csv".equalsIgnoreCase(extension);

        if (isNotCsvExtension) {
            throw new BadFileTypeException(GlobalExceptionEnum.BAD_FILE_TYPE.name());
        }
        validateNext(file, config);
    }
}
