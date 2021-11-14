package com.study.render;

import com.study.db.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

class AsciiTableRenderTest {

    public static final String EXPECTED_TABLE_WITH_THREE_ROWS = """
            ┌──────────────────────────┬─────────────────────────┬─────────────────────────┐
            │ID                        │NAME                     │MAIL                     │
            ├──────────────────────────┼─────────────────────────┼─────────────────────────┤
            │1                         │John                     │john@mail.com            │
            ├──────────────────────────┼─────────────────────────┼─────────────────────────┤
            │2                         │Jack                     │jack@mail.com            │
            ├──────────────────────────┼─────────────────────────┼─────────────────────────┤
            │3                         │Mark                     │mark@mail.com            │
            └──────────────────────────┴─────────────────────────┴─────────────────────────┘""";

    public static final String EXPECTED_TABLE_WITH_ONE_ROW = """
            ┌──────────────────────────┬─────────────────────────┬─────────────────────────┐
            │ID                        │NAME                     │MAIL                     │
            ├──────────────────────────┼─────────────────────────┼─────────────────────────┤
            │2                         │Jack                     │jack@mail.com            │
            └──────────────────────────┴─────────────────────────┴─────────────────────────┘""";

    public static final String EXPECTED_TABLE_WITHOUT_ROWS = """
            ┌──────────────────────────┬─────────────────────────┬─────────────────────────┐
            │ID                        │NAME                     │MAIL                     │
            └──────────────────────────┴─────────────────────────┴─────────────────────────┘""";

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private final AsciiTableRender tableRender = new AsciiTableRender(stream);

    @Test
    void should_renderTableWithThreeRows_when_passQueryResultsWithThreeRows() {
        tableRender.render(TestData.TABLE_WITH_ALL_FIELDS_AND_THREE_ROWS);
        Assertions.assertEquals(EXPECTED_TABLE_WITH_THREE_ROWS, stream.toString(StandardCharsets.UTF_8));
    }

    @Test
    void should_renderTableWithOneRow_when_passQueryResultsWithOneRow() {
        tableRender.render(TestData.TABLE_WITH_ALL_FIELDS_AND_ONE_ROW);
        Assertions.assertEquals(EXPECTED_TABLE_WITH_ONE_ROW, stream.toString(StandardCharsets.UTF_8));
    }

    @Test
    void should_renderEmptyTable_when_passQueryResultsWithoutRowsData() {
        tableRender.render(TestData.TABLE_WITHOUT_ROWS);
        Assertions.assertEquals(EXPECTED_TABLE_WITHOUT_ROWS, stream.toString(StandardCharsets.UTF_8));
    }
}