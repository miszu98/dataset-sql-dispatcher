package com.example.datasetsqldispatcher.controller;


import com.example.datasetsqldispatcher.dto.DatabaseModelDTO;
import com.example.datasetsqldispatcher.exception.BadFileTypeException;
import com.example.datasetsqldispatcher.exception.GlobalExceptionEnum;
import com.example.datasetsqldispatcher.service.impl.DatasetServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static com.example.datasetsqldispatcher.util.MockDataProvider.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class DatasetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private DatasetServiceImpl datasetService;

    @Test
    void shouldReturnStatus200WhileUploadingFile() throws Exception {
        final String filename = "mock-data.csv";
        final String type = "text/csv";
        final MockMultipartFile mockMultipartFile = getMockMultiPartFile(filename, type);
        final DatabaseModelDTO response = getMockDatabaseModel(filename, type);

        when(datasetService.getDatabaseModel(mockMultipartFile, null)).thenReturn(response);

        mockMvc.perform(multipart("/api/v1/dataset/transform")
                .file(mockMultipartFile))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.sqlScript").isNotEmpty(),
                        jsonPath("$.dataset").isNotEmpty(),
                        jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void shouldThrowExceptionWhenFileHasOtherExtensionThanCSV() throws Exception {
        final MockMultipartFile mockMultipartFile = getMockMultiPartFile("mock-data.xlsx", "text/xlsx");

        when(datasetService.getDatabaseModel(mockMultipartFile, null))
                .thenThrow(
                        new BadFileTypeException(messageSource.getMessage(GlobalExceptionEnum.BAD_FILE_TYPE.name(),
                                null, Locale.ENGLISH)));

        mockMvc.perform(multipart("/api/v1/dataset/transform")
                        .file(mockMultipartFile))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message").value("Wrong file type"),
                        jsonPath("$.errorCode").value("BAD_REQUEST"),
                        jsonPath("$.errorTime").isNotEmpty());

    }



}
