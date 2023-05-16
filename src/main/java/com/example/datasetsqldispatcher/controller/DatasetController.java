package com.example.datasetsqldispatcher.controller;

import com.example.datasetsqldispatcher.dto.DatabaseModelDTO;
import com.example.datasetsqldispatcher.dto.ExceptionResponse;
import com.example.datasetsqldispatcher.service.DatasetService;
import com.example.datasetsqldispatcher.util.HttpResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dataset")
@Tag(name = "Dataset API", description = "Operation for handling dataset structure")
public class DatasetController {

    private final DatasetService service;

    @PostMapping(value = "/transform", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Transform CSV dataset to tables and return sql script")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpResponseCode.OK,
                    description = "Returns created SQL script from dataset",
                    content = @Content(schema = @Schema(implementation = DatabaseModelDTO.class))
            ),
            @ApiResponse(
                    responseCode = HttpResponseCode.BAD_REQUEST,
                    description = "Probably wrong structure of file",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<DatabaseModelDTO> getSQLScript(
            @Parameter(
                    description = "File with data",
                    name = "file",
                    required = true) @RequestParam MultipartFile file,
            @Parameter(
                    description = "Database configuration",
                    name = "config",
                    required = true) @RequestParam(required = false) String config) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(service.getDatabaseModel(file, config));
    }

}
