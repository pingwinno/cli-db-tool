package com.study.db;

import com.study.model.QueryResult;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {
    private final DataSource dataSource;

    public QueryProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    public QueryResult executeQuery(String query) {

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(query)) {

            var columnNames = getColumnNames(resultSet.getMetaData());
            var rows = new ArrayList<List<String>>();
            while (resultSet.next()) {
                var row = new ArrayList<String>();
                for (String column : columnNames) {
                    row.add(resultSet.getString(column));
                }
                rows.add(row);
            }
            return QueryResult.builder()
                              .columnNames(columnNames)
                              .rows(rows)
                              .build();
        }
    }

    @SneakyThrows
    public int executeUpdate(String query) {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();) {
            var result = statement.executeUpdate(query);
            return result;
        }
    }

    private List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        var columns = new ArrayList<String>();
        final var columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(metaData.getColumnName(i));
        }
        return columns;
    }
}
