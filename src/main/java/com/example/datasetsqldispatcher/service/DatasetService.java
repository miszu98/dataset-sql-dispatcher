package com.example.datasetsqldispatcher.service;

import com.example.datasetsqldispatcher.dto.DatabaseModelDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface DatasetService {
    DatabaseModelDTO getDatabaseModel(MultipartFile file, String config) throws IOException;
}
