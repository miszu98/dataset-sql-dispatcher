package com.example.datasetsqldispatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignKey {
    private String sourceTable;
    private String refColumnName;
    private String columnName;
}
