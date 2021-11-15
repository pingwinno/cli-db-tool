package com.study.enums;

import java.util.Arrays;
import java.util.Objects;

public enum QueryType {
    SELECT("SELECT", true),
    INSERT("INSERT", false),
    UPDATE("UPDATE", false),
    DELETE("DELETE", false);

    private final String queryType;
    private final boolean isResultContainsData;

    QueryType(String queryType, boolean isResultContainsData) {
        this.queryType = queryType;
        this.isResultContainsData = isResultContainsData;
    }

    public static QueryType getQueryType(String queryType) {
        return Arrays.stream(QueryType.values())
                     .filter(q -> Objects.equals(q.queryType, queryType))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isResultContainsData() {
        return isResultContainsData;
    }
}
