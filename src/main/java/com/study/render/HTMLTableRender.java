package com.study.render;

import lombok.SneakyThrows;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.study.render.HTMLTemplates.BOTTOM;
import static com.study.render.HTMLTemplates.HEADER;
import static com.study.render.HTMLTemplates.TABLE_DATA_TEMPLATE;
import static com.study.render.HTMLTemplates.TABLE_ROW_TEMPLATE;

public class HTMLTableRender implements TableRender {
    private final OutputStream outputStream;

    public HTMLTableRender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @SneakyThrows
    @Override
    public void render(Map<String, List<String>> table) {

        var stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER);
        stringBuilder.append(String.format(TABLE_ROW_TEMPLATE, table.keySet().toArray()));
        var rowsNumber = table.values().stream().mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < rowsNumber; i++) {
            var row = new ArrayList<>();
            for (String columnName : table.keySet()) {
                row.add(table.get(columnName).get(i));
            }
            stringBuilder.append(String.format(TABLE_DATA_TEMPLATE, row.toArray()));
        }

        stringBuilder.append(BOTTOM);
        outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
    }
}
