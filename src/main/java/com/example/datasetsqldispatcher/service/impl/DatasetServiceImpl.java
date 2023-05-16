package com.example.datasetsqldispatcher.service.impl;

import com.example.datasetsqldispatcher.dto.DatabaseModelConfig;
import com.example.datasetsqldispatcher.dto.DatabaseModelDTO;
import com.example.datasetsqldispatcher.service.DatasetService;
import com.example.datasetsqldispatcher.util.DatabaseModelConfigDecoder;
import com.example.datasetsqldispatcher.util.validator.DatasetValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.tools.csv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.nonNull;


@Slf4j
@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService {

    private final SqlScriptService sqlScriptService;

    @Override
    public DatabaseModelDTO getDatabaseModel(MultipartFile file, String config)
            throws IOException {
        if (nonNull(config)) {
            DatabaseModelConfig decodedConfig = DatabaseModelConfigDecoder.decodeString(config);
            DatasetValidator.init(file, config);
            decodedConfig.getConfigs().forEach(System.out::println);
            // todo
        } else {
            return DatabaseModelDTO.builder()
                    .sqlScript(createSQLFile(file)).build();
        }
        return null;
    }

    private File createSQLFile(MultipartFile file) throws IOException {
        File tempFile = loadMultiPartFileToTempFile(file);
        BufferedReader bufferedReader = Files.newBufferedReader(tempFile.toPath());
        CSVReader csvReader = new CSVReader(bufferedReader);
        List<String[]> lines = csvReader.readAll();
        String[] headers = lines.get(0);
        String createTableSQL = getCreateTableSQL(headers);
        List<String> insertsIntoSQL = getInsertsIntoSQL(lines);
        return sqlScriptService.createSQLScript(createTableSQL, insertsIntoSQL);
    }

    private File loadMultiPartFileToTempFile(MultipartFile file) throws IOException {
        File tempFile = Files.createTempFile("temp", ".csv").toFile();
        file.transferTo(tempFile);
        return tempFile;
    }

    private List<String> getInsertsIntoSQL(List<String[]> lines) {
        List<String> sqls = new LinkedList<>();
        String[] headers = lines.get(0);
        InsertSetStep<Record> recordInsertSetStep = DSL.using(SQLDialect.POSTGRES)
                .insertInto(DSL.table("placeholder-table-name"));
        for (int i = 1; i < lines.size(); i++) {
            InsertSetMoreStep<Record> insertSetMoreStep = null;
            String[] values = lines.get(i);
            for (int j = 0; j < values.length; j++) {
                insertSetMoreStep = recordInsertSetStep.set(DSL.field(headers[j]), values[j]);
            }
            sqls.add(insertSetMoreStep.getSQL(ParamType.INLINED));
        }
        return sqls;
    }

    private String getCreateTableSQL(String[] headers) {
        CreateTableElementListStep createTableStep = DSL.using(SQLDialect.POSTGRES)
                .createTable("placeholder-table-name");

        for (int i = 0; i < headers.length; i++) {
            createTableStep = createTableStep.column(headers[i], SQLDataType.VARCHAR);
        }

        return createTableStep.getSQL();
    }


}

