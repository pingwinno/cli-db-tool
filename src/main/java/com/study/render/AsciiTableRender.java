package com.study.render;

import de.vandermeer.asciitable.AsciiTable;
import lombok.SneakyThrows;
import org.fusesource.jansi.AnsiConsole;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsciiTableRender implements TableRender {
    private final OutputStream outputStream;

    public AsciiTableRender(OutputStream outputStream) {
        this.outputStream = outputStream;
        AnsiConsole.systemInstall();
    }

    @SneakyThrows
    @Override
    public void render(Map<String, List<String>> table) {
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow(table.keySet());
        asciiTable.addRule();
        int rowsNumber = table.values().stream().findFirst().map(List::size).orElse(0);
        for (int i = 0; i < rowsNumber; i++) {
            var row = new ArrayList<>();
            for (String columnName : table.keySet()) {
                row.add(table.get(columnName).get(i));
            }
            asciiTable.addRow(row);
            asciiTable.addRule();
        }
        outputStream.write(asciiTable.render().getBytes(StandardCharsets.UTF_8));
    }
}
