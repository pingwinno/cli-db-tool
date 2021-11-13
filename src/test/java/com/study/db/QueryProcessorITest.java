package com.study.db;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.study.db.TestData.DELETE_QUERY;
import static com.study.db.TestData.EXPECTED_TABLE_ID_COLUMN_ONLY;
import static com.study.db.TestData.INSERT_QUERY;
import static com.study.db.TestData.NAME_LABEL;
import static com.study.db.TestData.SELECT_QUERY_WITH_ASTERISK;
import static com.study.db.TestData.SELECT_QUERY_WITH_ID;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_ONE_ROW;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_THREE_ROWS;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS;
import static com.study.db.TestData.UPDATED_NAME;
import static com.study.db.TestData.UPDATE_QUERY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryProcessorITest {
    private static final String CONNECTION_STRING = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init_test_table.sql';";
    private final ConnectionManager connectionManager = new ConnectionManager(CONNECTION_STRING, "sa", "");
    private final QueryProcessor queryProcessor = new QueryProcessor(connectionManager);

    @Test
    void should_returnTableWithData_when_passSelectQueryWithAsteriskColumnParameter() throws SQLException {
        var actualResult = queryProcessor.select(SELECT_QUERY_WITH_ASTERISK);
        assertEquals(TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS, actualResult);
    }

    @Test
    void should_returnTableWithIdOnly_when_passSelectQueryWithIdColumnParameter() throws SQLException {
        var actualResult = queryProcessor.select(SELECT_QUERY_WITH_ID);
        assertEquals(EXPECTED_TABLE_ID_COLUMN_ONLY, actualResult);
    }


    @Test
    void should_returnOneAndFindUpdatedRow_when_updateRow() throws SQLException {
        var actualResult = queryProcessor.update(UPDATE_QUERY);
        assertEquals(1, actualResult);
        var updatedRow = queryProcessor.select(TestData.SELECT_UPDATED_ROW_WITH_NAME_ONLY);
        assertEquals(UPDATED_NAME, updatedRow.get(NAME_LABEL).get(0));
    }

    @Test
    void should_returnOneAndTableWithThreeRows_when_insertOneRow() throws SQLException {
        var actualResult = queryProcessor.insert(INSERT_QUERY);
        assertEquals(1, actualResult);
        var updatedTable = queryProcessor.select(SELECT_QUERY_WITH_ASTERISK);
        assertEquals(TABLE_WITH_ALL_FIELDS_AND_THREE_ROWS, updatedTable);
    }

    @Test
    void should_returnOneAndTableWithThreeRows_when_deleteOneRow() throws SQLException {
        var actualResult = queryProcessor.delete(DELETE_QUERY);
        assertEquals(1, actualResult);
        var updatedTable = queryProcessor.select(SELECT_QUERY_WITH_ASTERISK);
        assertEquals(TABLE_WITH_ALL_FIELDS_AND_ONE_ROW, updatedTable);
    }
}