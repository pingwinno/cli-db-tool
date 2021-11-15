package com.study.render;

import com.study.db.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

class HTMLTableRenderTest {

    public static final String EXPECTED_TABLE_WITH_THREE_ROWS = """
            <!DOCTYPE html>
            <html>
            <style>
            table, th, td {
              border:1px solid black;
            }
            </style>
            <body>


            <table style="width:100%">
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>MAIL</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>John</td>
                    <td>john@mail.com</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Jack</td>
                    <td>jack@mail.com</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Mark</td>
                    <td>mark@mail.com</td>
                </tr>
            </table>

            </body>
            </html>""";

    public static final String EXPECTED_TABLE_WITH_ONE_ROW = """
            <!DOCTYPE html>
            <html>
            <style>
            table, th, td {
              border:1px solid black;
            }
            </style>
            <body>


            <table style="width:100%">
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>MAIL</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>John</td>
                    <td>john@mail.com</td>
                </tr>
            </table>

            </body>
            </html>""";

    public static final String EXPECTED_TABLE_WITHOUT_ROWS = """
            <!DOCTYPE html>
            <html>
            <style>
            table, th, td {
              border:1px solid black;
            }
            </style>
            <body>


            <table style="width:100%">
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>MAIL</th>
                </tr>
            </table>

            </body>
            </html>""";

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private final HTMLTableRender tableRender = new HTMLTableRender(stream);

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