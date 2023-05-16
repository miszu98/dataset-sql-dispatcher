package com.example.datasetsqldispatcher.util;

import com.example.datasetsqldispatcher.dto.DatabaseModelDTO;
import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

@UtilityClass
public class MockDataProvider {

    public static MockMultipartFile getMockMultiPartFile(String filename, String type) {
        return new MockMultipartFile(
                "file",
                filename,
                type,
                new byte[]{});
    }

    public static File getMockSqlScript() throws IOException {
        return Files.createTempFile("script", ".sql").toFile();
    }

    public static File getMockDataset() throws IOException {
        return Files.createTempFile("dataset", ".csv").toFile();
    }

    public static DatabaseModelDTO getMockDatabaseModel(String filename, String type) throws IOException {
        final MockMultipartFile mockMultipartFile = getMockMultiPartFile(filename, type);

        final File sqlScript = getMockSqlScript();
        sqlScript.deleteOnExit();

        final File dataset = getMockDataset();
        dataset.deleteOnExit();
        mockMultipartFile.transferTo(dataset);

        return DatabaseModelDTO.builder()
                .dataset(dataset)
                .createdAt(LocalDateTime.now())
                .sqlScript(sqlScript).build();
    }

    public static String getDatabaseConfigJson() {
        return """
                {
                    "configs": [
                        {
                            "columns": [
                                "col1", "col2", "col3"
                            ],
                            "tableName": "table-name",
                            "foreignKeys": [
                                {
                                    "sourceTable": "source-table",
                                    "refColumnName": "ref-column-name",
                                    "columnName": "col-name"
                                }
                            ]
                        },
                        {
                            "columns": [
                                "col1", "col2", "col3"
                            ],
                            "tableName": "table-name",
                            "foreignKeys": [
                                {
                                    "sourceTable": "source-table",
                                    "refColumnName": "ref-column-name",
                                    "columnName": "col-name"
                                }
                            ]
                        }
                    ]
                }
                """;
    }
}
