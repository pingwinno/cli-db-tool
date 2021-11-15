package com.study.service;

import com.study.db.QueryProcessor;
import com.study.render.TableRender;
import lombok.SneakyThrows;

import java.util.Arrays;

public class QueryProcessinService {

    private final TableRender[] tableRenders;
    private final QueryProcessor queryProcessor;

    public QueryProcessinService(QueryProcessor queryProcessor, TableRender... tableRenders) {
        this.tableRenders = tableRenders;
        this.queryProcessor = queryProcessor;
    }

    @SneakyThrows
    public void select(String query) {
        var tableResult = queryProcessor.select(query);
        Arrays.stream(tableRenders).forEach(tableRender -> tableRender.render(tableResult));
    }

    @SneakyThrows
    public int update(String query) {
        return queryProcessor.update(query);
    }

    @SneakyThrows
    public int insert(String query) {
        return queryProcessor.insert(query);
    }

    @SneakyThrows
    public int delete(String query) {
        return queryProcessor.delete(query);
    }
}
