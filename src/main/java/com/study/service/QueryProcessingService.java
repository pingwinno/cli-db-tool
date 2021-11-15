package com.study.service;

import com.study.db.QueryProcessor;
import com.study.enums.QueryType;
import com.study.render.TableRender;

import java.util.Arrays;
import java.util.Map;

public class QueryProcessingService {

    private final static Map<QueryType, String> QUERY_TYPE_TO_VERB = Map.of(QueryType.INSERT, "inserted",
            QueryType.DELETE, "deleted", QueryType.UPDATE, "updated");
    private static final String MESSAGE_TEMPLATE = "%s rows was %s %n";
    private final TableRender[] tableRenders;
    private final QueryProcessor queryProcessor;

    public QueryProcessingService(QueryProcessor queryProcessor, TableRender... tableRenders) {
        this.tableRenders = tableRenders;
        this.queryProcessor = queryProcessor;
    }

    public void process(String query) {
        var queryType = getQueryTypeFromQuery(query);
        if (queryType.isResultContainsData()) {
            var tableResult = queryProcessor.executeQuery(query);
            Arrays.stream(tableRenders)
                  .forEach(tableRender -> tableRender.render(tableResult));
        } else {
            queryProcessor.executeUpdate(query);
        }
    }

    private QueryType getQueryTypeFromQuery(String query) {
        return QueryType.getQueryType(query.split(" ")[0]);
    }

    private void printNumberOfAffectedRows(QueryType queryType, int numberOfAffectedRows) {
        System.out.printf(MESSAGE_TEMPLATE, numberOfAffectedRows, QUERY_TYPE_TO_VERB.get(queryType));
    }
}
