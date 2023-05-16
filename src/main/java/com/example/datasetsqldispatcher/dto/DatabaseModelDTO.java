package com.example.datasetsqldispatcher.dto;

import lombok.Builder;

import java.io.File;
import java.time.LocalDateTime;

public record DatabaseModelDTO(LocalDateTime createdAt,
                               File dataset,
                               File sqlScript) {
    @Builder public DatabaseModelDTO {}
}
