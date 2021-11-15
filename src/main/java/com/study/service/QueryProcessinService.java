package com.study.service;

import com.study.db.DbRepository;
import com.study.render.TableRender;
import lombok.SneakyThrows;

import java.util.Arrays;

public class QueryProcessinService {

    private final TableRender[] tableRenders;
    private final DbRepository dbRepository;

    public QueryProcessinService(DbRepository dbRepository, TableRender... tableRenders) {
        this.tableRenders = tableRenders;
        this.dbRepository = dbRepository;
    }

    @SneakyThrows
    public void select(String query) {
        var tableResult = dbRepository.select(query);
        Arrays.stream(tableRenders).forEach(tableRender -> tableRender.render(tableResult));
    }

    @SneakyThrows
    public int update(String query) {
        return dbRepository.update(query);
    }

    @SneakyThrows
    public int insert(String query) {
        return dbRepository.insert(query);
    }

    @SneakyThrows
    public int delete(String query) {
        return dbRepository.delete(query);
    }
}
