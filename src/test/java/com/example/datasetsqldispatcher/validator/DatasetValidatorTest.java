package com.example.datasetsqldispatcher.validator;

import com.example.datasetsqldispatcher.exception.BadFileTypeException;
import com.example.datasetsqldispatcher.exception.GlobalExceptionEnum;
import com.example.datasetsqldispatcher.util.validator.DatasetValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import static com.example.datasetsqldispatcher.util.MockDataProvider.getMockMultiPartFile;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DatasetValidatorTest {


    @Test
    void shouldThrowExceptionWhenFileIsNull() {
        final MultipartFile file = null;
        final String config = null;

        FileNotFoundException fileNotFoundException = assertThrows(FileNotFoundException.class,
                () -> DatasetValidator.init(file, config));

        assertEquals(GlobalExceptionEnum.FILE_NOT_FOUND.name(), fileNotFoundException.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenFileIsNotNull() {
        final MultipartFile file = new MockMultipartFile("file",
                "mock-file.csv", "text/csv", new byte[]{});
        final String config = null;

        assertDoesNotThrow(() -> DatasetValidator.init(file, config));
    }

    @Test
    void shouldThrowExceptionWhenFileExtensionIsOtherThanCSV() {
        final MultipartFile file = getMockMultiPartFile("mock-data.txt", null);
        final String config = null;

        BadFileTypeException badFileTypeException = assertThrows(BadFileTypeException.class,
                () -> DatasetValidator.init(file, config));

        assertEquals(GlobalExceptionEnum.BAD_FILE_TYPE.name(), badFileTypeException.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenFileExtensionIsCSV() {
        final MultipartFile file = getMockMultiPartFile("mock-data.csv", "text/csv");
        final String config = null;

        assertDoesNotThrow(() -> DatasetValidator.init(file, config));
    }
}
