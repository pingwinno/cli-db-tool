package com.study.render;

import com.study.model.QueryResult;
import de.vandermeer.asciitable.AsciiTable;
import lombok.SneakyThrows;
import org.fusesource.jansi.AnsiConsole;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class AsciiTableRender implements TableRender {
    private final OutputStream outputStream;

    public AsciiTableRender(OutputStream outputStream) {
        this.outputStream = outputStream;
        AnsiConsole.systemInstall();
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
        outputStream.write(asciiTable.render()
                                     .getBytes(StandardCharsets.UTF_8));
    }
}
