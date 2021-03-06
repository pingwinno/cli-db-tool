package com.study.db;

import com.study.model.QueryResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.study.db.TestData.COLUMN_NAMES;
import static com.study.db.TestData.EXPECTED_TABLE_ID_COLUMN_ONLY;
import static com.study.db.TestData.ID_LABEL;
import static com.study.db.TestData.INSERT_QUERY;
import static com.study.db.TestData.JACK_TEST_ENTITY;
import static com.study.db.TestData.JOHN_TEST_ENTITY;
import static com.study.db.TestData.MAIL_LABEL;
import static com.study.db.TestData.NAME_LABEL;
import static com.study.db.TestData.SELECT_QUERY_WITH_ASTERISK;
import static com.study.db.TestData.SELECT_QUERY_WITH_ID;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS;
import static com.study.db.TestData.UPDATE_QUERY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class QueryProcessorTest {

    private final ConnectionFactory CONNECTION_FACTORY = mock(ConnectionFactory.class);
    private final Connection CONNECTION = mock(Connection.class);
    private final Statement STATEMENT = mock(Statement.class);
    private final ResultSet RESULT_SET = mock(ResultSet.class);
    private final ResultSetMetaData RESULT_SET_META_DATA = mock(ResultSetMetaData.class);
    private final QueryProcessor queryProcessor = new QueryProcessor(CONNECTION_FACTORY);

    @BeforeEach
    void initMocks() throws SQLException {
        when(CONNECTION_FACTORY.getConnection()).thenReturn(CONNECTION);
        when(CONNECTION.createStatement()).thenReturn(STATEMENT);
        when(STATEMENT.executeQuery(anyString())).thenReturn(RESULT_SET);
        doNothing().when(STATEMENT)
                   .close();
        when(RESULT_SET.getMetaData()).thenReturn(RESULT_SET_META_DATA);
    }

    @AfterEach
    void verifyNoMoreInteraction() throws SQLException {
        verify(STATEMENT).close();
        verify(CONNECTION).close();
        verifyNoMoreInteractions(STATEMENT);
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

        var actualResult = queryProcessor.executeQuery(SELECT_QUERY_WITH_ASTERISK);

        assertEquals(TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS, actualResult);

        verify(STATEMENT).executeQuery(SELECT_QUERY_WITH_ASTERISK);
        verify(RESULT_SET).getMetaData();
        verify(RESULT_SET_META_DATA).getColumnName(1);
        verify(RESULT_SET_META_DATA).getColumnName(2);
        verify(RESULT_SET_META_DATA).getColumnName(3);
        verify(RESULT_SET_META_DATA).getColumnCount();
        verify(RESULT_SET, times(2)).getString(ID_LABEL);
        verify(RESULT_SET, times(2)).getString(NAME_LABEL);
        verify(RESULT_SET, times(2)).getString(MAIL_LABEL);
        verify(RESULT_SET, times(3)).next();
        verify(RESULT_SET).close();
    }

    @Test
    void should_returnTableWithIdOnly_when_passSelectQueryWithIdColumnParameter() throws SQLException {
        when(RESULT_SET_META_DATA.getColumnName(1)).thenReturn(ID_LABEL);
        when(RESULT_SET_META_DATA.getColumnCount()).thenReturn(1);
        when(RESULT_SET.next()).thenReturn(true, true, false);
        when(RESULT_SET.getString(ID_LABEL)).thenReturn(JOHN_TEST_ENTITY.id(), JACK_TEST_ENTITY.id());

        var actualResult = queryProcessor.executeQuery(SELECT_QUERY_WITH_ID);

        assertEquals(EXPECTED_TABLE_ID_COLUMN_ONLY, actualResult);

        verify(STATEMENT).executeQuery(SELECT_QUERY_WITH_ID);
        verify(RESULT_SET).getMetaData();
        verify(RESULT_SET_META_DATA).getColumnName(1);
        verify(RESULT_SET_META_DATA).getColumnCount();
        verify(RESULT_SET, times(2)).getString(ID_LABEL);
        verify(RESULT_SET, times(3)).next();
        verify(RESULT_SET).close();
    }

    @Test
    void should_returnEmptyTable_when_passSelectQueryWithAllParamsButTableInDatabaseIsEmpty() throws SQLException {
        var expectedResult = QueryResult.builder()
                                        .columnNames(COLUMN_NAMES)
                                        .rows(List.of())
                                        .build();
        when(RESULT_SET_META_DATA.getColumnName(1)).thenReturn(ID_LABEL);
        when(RESULT_SET_META_DATA.getColumnName(2)).thenReturn(NAME_LABEL);
        when(RESULT_SET_META_DATA.getColumnName(3)).thenReturn(MAIL_LABEL);
        when(RESULT_SET_META_DATA.getColumnCount()).thenReturn(3);
        when(RESULT_SET.next()).thenReturn(false);

        var actualResult = queryProcessor.executeQuery(SELECT_QUERY_WITH_ID);

        assertEquals(expectedResult, actualResult);

        verify(STATEMENT).executeQuery(SELECT_QUERY_WITH_ID);
        verify(RESULT_SET).getMetaData();
        verify(RESULT_SET_META_DATA).getColumnName(1);
        verify(RESULT_SET_META_DATA).getColumnName(2);
        verify(RESULT_SET_META_DATA).getColumnName(3);
        verify(RESULT_SET_META_DATA).getColumnCount();
        verify(RESULT_SET).next();
        verify(RESULT_SET).getMetaData();
        verify(RESULT_SET).close();
        verifyNoMoreInteractions(RESULT_SET);
    }

    @Test
    void should_returnOne_when_updateRow() throws SQLException {
        when(STATEMENT.executeUpdate(UPDATE_QUERY)).thenReturn(1);

        var actualResult = queryProcessor.executeUpdate(UPDATE_QUERY);

        assertEquals(1, actualResult);
        verify(STATEMENT).executeUpdate(UPDATE_QUERY);
    }

    @Test
    void should_returnOne_when_insertRow() throws SQLException {
        when(STATEMENT.executeUpdate(INSERT_QUERY)).thenReturn(1);

        var actualResult = queryProcessor.executeUpdate(INSERT_QUERY);

        assertEquals(1, actualResult);
        verify(STATEMENT).executeUpdate(INSERT_QUERY);
    }

    @Test
    void should_returnOne_when_deleteRow() throws SQLException {
        when(STATEMENT.executeUpdate(INSERT_QUERY)).thenReturn(1);

        var actualResult = queryProcessor.executeUpdate(INSERT_QUERY);

        assertEquals(1, actualResult);
        verify(STATEMENT).executeUpdate(INSERT_QUERY);
    }
}