package com.study.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static com.study.db.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class QueryProcessorTest {

    private static final ConnectionManager CONNECTION_MANAGER = mock(ConnectionManager.class);
    private static final Statement STATEMENT = mock(Statement.class);
    private static final ResultSet RESULT_SET = mock(ResultSet.class);
    private static final ResultSetMetaData RESULT_SET_META_DATA = mock(ResultSetMetaData.class);
    private static final QueryProcessor queryProcessor = new QueryProcessor(CONNECTION_MANAGER);

    @BeforeAll
    static void initMocks() throws SQLException {
        when(CONNECTION_MANAGER.createStatement()).thenReturn(STATEMENT);
        when(STATEMENT.executeQuery(anyString())).thenReturn(RESULT_SET);
        doNothing().when(STATEMENT).close();
        when(RESULT_SET.getMetaData()).thenReturn(RESULT_SET_META_DATA);
    }

    @Test
    void should_returnTableWithData_when_passSelectQueryWithAsteriskColumnParameter() throws SQLException {
        when(RESULT_SET_META_DATA.getColumnName(1)).thenReturn(ID_LABEL);
        when(RESULT_SET_META_DATA.getColumnName(2)).thenReturn(NAME_LABEL);
        when(RESULT_SET_META_DATA.getColumnName(3)).thenReturn(MAIL_LABEL);
        when(RESULT_SET_META_DATA.getColumnCount()).thenReturn(3);
        when(RESULT_SET.next()).thenReturn(true, true, false);
        when(RESULT_SET.getString(ID_LABEL)).thenReturn(JOHN_TEST_ENTITY.id(), JACK_TEST_ENTITY.id());
        when(RESULT_SET.getString(NAME_LABEL)).thenReturn(JOHN_TEST_ENTITY.name(), JACK_TEST_ENTITY.name());
        when(RESULT_SET.getString(MAIL_LABEL)).thenReturn(JOHN_TEST_ENTITY.mail(), JACK_TEST_ENTITY.mail());

        var actualResult = queryProcessor.select(SELECT_QUERY_WITH_ASTERISK);

        assertEquals(EXPECTED_TABLE_ALL_COLUMNS, actualResult);

        verify(RESULT_SET, times(2)).getString(ID_LABEL);
        verify(RESULT_SET, times(2)).getString(NAME_LABEL);
        verify(RESULT_SET, times(2)).getString(MAIL_LABEL);
        verify(RESULT_SET, times(3)).next();
        verify(RESULT_SET_META_DATA).getColumnName(1);
        verify(RESULT_SET_META_DATA).getColumnName(2);
        verify(RESULT_SET_META_DATA).getColumnName(3);
        verify(RESULT_SET_META_DATA).getColumnCount();
        verify(STATEMENT).close();
    }

    @Test
    void should_returnTableWithData_when_passSelectQueryWithIDColumnParameter() throws SQLException {
        when(RESULT_SET_META_DATA.getColumnName(1)).thenReturn(ID_LABEL);
        when(RESULT_SET_META_DATA.getColumnCount()).thenReturn(1);
        when(RESULT_SET.next()).thenReturn(true, true, false);
        when(RESULT_SET.getString(ID_LABEL)).thenReturn(JOHN_TEST_ENTITY.id(), JACK_TEST_ENTITY.id());

        var actualResult = queryProcessor.select(SELECT_QUERY_WITH_ID);

        assertEquals(EXPECTED_TABLE_ID_COLUMN_ONLY, actualResult);

        verify(RESULT_SET, times(2)).getString(ID_LABEL);
        verify(RESULT_SET, times(3)).next();
        verify(RESULT_SET_META_DATA).getColumnName(1);
        verify(RESULT_SET_META_DATA).getColumnCount();
        verify(STATEMENT).close();
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }

    @Test
    void delete() {
    }


}