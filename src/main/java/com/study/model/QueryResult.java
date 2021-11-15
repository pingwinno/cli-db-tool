package com.study.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QueryResult {
    List<String> columnNames;
    List<List<String>> rows;
}
