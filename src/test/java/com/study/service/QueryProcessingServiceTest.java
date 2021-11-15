package com.study.service;

import com.study.db.QueryProcessor;
import com.study.model.QueryResult;
import com.study.render.AsciiTableGenerator;
import com.study.render.HTMLTableGenerator;
import org.junit.jupiter.api.Test;

import static com.study.db.TestData.DELETE_QUERY;
import static com.study.db.TestData.INSERT_QUERY;
import static com.study.db.TestData.SELECT_QUERY_WITH_ASTERISK;
import static com.study.db.TestData.UPDATE_QUERY;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class QueryProcessingServiceTest {

    private static final int EXPECTED_NUMBER_OF_CHANGES = 1;
    private final HTMLTableGenerator htmlRender = mock(HTMLTableGenerator.class);
    private final AsciiTableGenerator asciiRender = mock(AsciiTableGenerator.class);
    private final QueryProcessor queryProcessor = mock(QueryProcessor.class);
    private final QueryProcessingService queryProcessingService = new QueryProcessingService(queryProcessor, htmlRender,
            asciiRender);

    @Test
    void should_queryTableAndRenderTable_when_callSelect() {
        var queryResult = QueryResult.builder()
                                     .build();
        when(queryProcessor.executeQuery(SELECT_QUERY_WITH_ASTERISK)).thenReturn(queryResult);
        doNothing().when(htmlRender)
                   .render(queryResult);
        doNothing().when(asciiRender)
                   .render(queryResult);
        queryProcessingService.process(SELECT_QUERY_WITH_ASTERISK);
        verify(queryProcessor).executeQuery(SELECT_QUERY_WITH_ASTERISK);
        verify(htmlRender).render(queryResult);
        verify(asciiRender).render(queryResult);
    }

    @Test
    void should_returnNumberOfUpdatedEntities_when_callUpdate() {
        when(queryProcessor.executeUpdate(UPDATE_QUERY)).thenReturn(EXPECTED_NUMBER_OF_CHANGES);
        queryProcessingService.process(UPDATE_QUERY);
        verify(queryProcessor).executeUpdate(UPDATE_QUERY);
        verifyNoInteractions(htmlRender, asciiRender);
    }

    @Test
    void should_returnNumberOfInsertedEntities_when_callInsert() {
        when(queryProcessor.executeUpdate(INSERT_QUERY)).thenReturn(EXPECTED_NUMBER_OF_CHANGES);
        queryProcessingService.process(INSERT_QUERY);
        verify(queryProcessor).executeUpdate(INSERT_QUERY);
        verifyNoInteractions(htmlRender, asciiRender);
    }

    @Test
    void should_returnNumberOfDeletedEntities_when_callDelete() {
        when(queryProcessor.executeUpdate(DELETE_QUERY)).thenReturn(EXPECTED_NUMBER_OF_CHANGES);
        queryProcessingService.process(DELETE_QUERY);
        verify(queryProcessor).executeUpdate(DELETE_QUERY);
        verifyNoInteractions(htmlRender, asciiRender);
    }
}