package com.study.db;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryProcessor {
    private final ConnectionManager connectionManager;

    public QueryProcessor(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Map<String, List<String>> select(String query) throws SQLException {
        var queryResult = new LinkedHashMap<String, List<String>>();
        try (var statement = connectionManager.createStatement()) {
            var resultSet = statement.executeQuery(query);
            var columns = getColumnNames(resultSet.getMetaData());
            while (resultSet.next()) {
                for (String column : columns) {
                    if (!queryResult.containsKey(column)) {
                        queryResult.put(column, new ArrayList<>());
                    }
                    queryResult.get(column).add(resultSet.getString(column));
                }
            }
        }
        return queryResult;
    }

    public int update(String query) throws SQLException {
        return executeUpdate(query);
    }

    public int insert(String query) throws SQLException {
        return executeUpdate(query);
    }

    public int delete(String query) throws SQLException {
        return executeUpdate(query);
    }

    private int executeUpdate(String query) throws SQLException {
        try (var statement = connectionManager.createStatement()) {
            return statement.executeUpdate(query);
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
