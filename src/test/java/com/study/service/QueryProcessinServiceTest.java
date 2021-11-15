package com.study.service;

import com.study.db.DbRepository;
import com.study.render.AsciiTableRender;
import com.study.render.HTMLTableRender;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static com.study.db.TestData.DELETE_QUERY;
import static com.study.db.TestData.INSERT_QUERY;
import static com.study.db.TestData.SELECT_QUERY_WITH_ASTERISK;
import static com.study.db.TestData.UPDATE_QUERY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class QueryProcessinServiceTest {

    private static final int EXPECTED_NUMBER_OF_CHANGES = 1;
    private final HTMLTableRender htmlRender = mock(HTMLTableRender.class);
    private final AsciiTableRender asciiRender = mock(AsciiTableRender.class);
    private final DbRepository dbRepository = mock(DbRepository.class);
    private final QueryProcessinService queryProcessinService = new QueryProcessinService(dbRepository, htmlRender, asciiRender);

    @Test
    void should_queryTableAndRenderTable_when_callSelect() throws SQLException {
        when(dbRepository.select(SELECT_QUERY_WITH_ASTERISK)).thenReturn(Map.of());
        doNothing().when(htmlRender).render(Map.of());
        doNothing().when(asciiRender).render(Map.of());
        queryProcessinService.select(SELECT_QUERY_WITH_ASTERISK);
        verify(dbRepository).select(SELECT_QUERY_WITH_ASTERISK);
        verify(htmlRender).render(Map.of());
        verify(asciiRender).render(Map.of());
    }

    @Test
    void should_returnNumberOfUpdatedEntities_when_callUpdate() throws SQLException {
        when(dbRepository.update(UPDATE_QUERY)).thenReturn(EXPECTED_NUMBER_OF_CHANGES);
        var actualNumberOfChanges = queryProcessinService.update(UPDATE_QUERY);
        assertEquals(EXPECTED_NUMBER_OF_CHANGES, actualNumberOfChanges);
        verify(dbRepository).update(UPDATE_QUERY);
        verifyNoInteractions(htmlRender, asciiRender);
    }

    @Test
    void should_returnNumberOfInsertedEntities_when_callInsert() throws SQLException {
        when(dbRepository.insert(INSERT_QUERY)).thenReturn(EXPECTED_NUMBER_OF_CHANGES);
        var actualNumberOfChanges = queryProcessinService.insert(INSERT_QUERY);
        assertEquals(EXPECTED_NUMBER_OF_CHANGES, actualNumberOfChanges);
        verify(dbRepository).insert(INSERT_QUERY);
        verifyNoInteractions(htmlRender, asciiRender);
    }

    @Test
    void should_returnNumberOfDeletedEntities_when_callDelete() throws SQLException {
        when(dbRepository.delete(DELETE_QUERY)).thenReturn(EXPECTED_NUMBER_OF_CHANGES);
        var actualNumberOfChanges = queryProcessinService.delete(DELETE_QUERY);
        assertEquals(EXPECTED_NUMBER_OF_CHANGES, actualNumberOfChanges);
        verify(dbRepository).delete(DELETE_QUERY);
        verifyNoInteractions(htmlRender, asciiRender);
    }
}