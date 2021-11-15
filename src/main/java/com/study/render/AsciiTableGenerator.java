package com.study.render;

import com.study.factory.Render;
import com.study.model.QueryResult;
import de.vandermeer.asciitable.AsciiTable;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class AsciiTableGenerator extends TableGenerator {

    public AsciiTableGenerator(Render render) {
        super(render);
    }

    @SneakyThrows
    @Override
    public void render(QueryResult table) {
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow(table.getColumnNames());
        asciiTable.addRule();
        if (Objects.nonNull(table.getRows()) && !table.getRows()
                                                      .isEmpty()) {
            for (List<String> row : table.getRows()) {
                asciiTable.addRow(row);
                asciiTable.addRule();
            }
        }
        getRender().render(asciiTable.render()
                                     .getBytes(StandardCharsets.UTF_8));
    }
}
