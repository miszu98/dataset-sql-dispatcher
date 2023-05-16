package com.example.datasetsqldispatcher.service.impl;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class SqlScriptService {

    public File createSQLScript(String createTableSQL, List<String> insertsSQL) throws IOException {
        File file = new File("script.sql");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(createTableSQL + ";" + "\n");
        for (String insertSQL : insertsSQL) {
            bufferedWriter.write(insertSQL + ";" + "\n");
        }
        bufferedWriter.close();
        return file;
    }

}
