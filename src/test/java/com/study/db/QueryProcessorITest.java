package com.study.db;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.study.db.TestData.DELETE_QUERY;
import static com.study.db.TestData.EXPECTED_TABLE_ID_COLUMN_ONLY;
import static com.study.db.TestData.INSERT_QUERY;
import static com.study.db.TestData.SELECT_QUERY_WITH_ASTERISK;
import static com.study.db.TestData.SELECT_QUERY_WITH_ID;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_ONE_ROW;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_THREE_ROWS;
import static com.study.db.TestData.TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS;
import static com.study.db.TestData.UPDATED_NAME;
import static com.study.db.TestData.UPDATE_QUERY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO Fix integration tests
class QueryProcessorITest {
    private static final String CONNECTION_STRING = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init_test_table.sql';";
    private final ConnectionFactory connectionFactory = new ConnectionFactory(CONNECTION_STRING, "sa", "");
    private final QueryProcessor queryProcessor = new QueryProcessor(connectionFactory);

    @Test
    void should_returnTableWithData_when_passSelectQueryWithAsteriskColumnParameter() {
        var actualResult = queryProcessor.executeQuery(SELECT_QUERY_WITH_ASTERISK);
        assertEquals(TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS, actualResult);
    }

    @Test
    void should_returnTableWithIdOnly_when_passSelectQueryWithIdColumnParameter() {
        var actualResult = queryProcessor.executeQuery(SELECT_QUERY_WITH_ID);
        assertEquals(EXPECTED_TABLE_ID_COLUMN_ONLY, actualResult);
    }

    @Disabled
    @Test
    void should_returnOneAndFindUpdatedRow_when_updateRow() {
        var actualResult = queryProcessor.executeUpdate(UPDATE_QUERY);
        assertEquals(1, actualResult);
        var updatedRow = queryProcessor.executeQuery(TestData.SELECT_UPDATED_ROW_WITH_NAME_ONLY);
        assertTrue(updatedRow.getRows()
                             .stream()
                             .anyMatch(row -> row.contains(UPDATED_NAME)));
    }

    @Disabled
    @Test
    void should_returnOneAndTableWithThreeRows_when_insertOneRow() {
        var actualResult = queryProcessor.executeUpdate(INSERT_QUERY);
        assertEquals(1, actualResult);
        var updatedTable = queryProcessor.executeQuery(SELECT_QUERY_WITH_ASTERISK);
        assertEquals(TABLE_WITH_ALL_FIELDS_AND_THREE_ROWS, updatedTable);
    }

    @Disabled
    @Test
    void should_returnOneAndTableWithThreeRows_when_deleteOneRow() {
        var actualResult = queryProcessor.executeUpdate(DELETE_QUERY);
        assertEquals(1, actualResult);
        var updatedTable = queryProcessor.executeQuery(SELECT_QUERY_WITH_ASTERISK);
        assertEquals(TABLE_WITH_ALL_FIELDS_AND_ONE_ROW, updatedTable);
    }
}