package com.study.render;

import com.study.factory.Render;
import com.study.model.QueryResult;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static com.study.render.HTMLTemplates.BOTTOM;
import static com.study.render.HTMLTemplates.HEADER;
import static com.study.render.HTMLTemplates.TABLE_DATA_TEMPLATE;
import static com.study.render.HTMLTemplates.TABLE_HEADER_TEMPLATE;
import static com.study.render.HTMLTemplates.TABLE_ROW_END;
import static com.study.render.HTMLTemplates.TABLE_ROW_START;


public class HTMLTableGenerator extends TableGenerator {

    public HTMLTableGenerator(Render render) {
        super(render);
    }

    @SneakyThrows
    @Override
    public void render(QueryResult table) {
        var tableBuilder = new StringBuilder();
        tableBuilder.append(HEADER);
        tableBuilder.append(TABLE_ROW_START);
        var columnsHeadBuilder = new StringBuilder();
        for (String columnName : table.getColumnNames()) {
            columnsHeadBuilder.append(String.format(TABLE_HEADER_TEMPLATE, columnName));
        }
        tableBuilder.append(columnsHeadBuilder);
        tableBuilder.append(TABLE_ROW_END);

        if (Objects.nonNull(table.getRows()) && !table.getRows()
                                                      .isEmpty()) {
            for (List<String> row : table.getRows()) {
                tableBuilder.append(TABLE_ROW_START);
                var rowBuilder = new StringBuilder();
                for (String field : row) {
                    rowBuilder.append(String.format(TABLE_DATA_TEMPLATE, field));
                }
                tableBuilder.append(rowBuilder);
                tableBuilder.append(TABLE_ROW_END);
            }
        }
        tableBuilder.append(BOTTOM);
        getRender().render(tableBuilder.toString()
                                       .getBytes(StandardCharsets.UTF_8));
    }
}
