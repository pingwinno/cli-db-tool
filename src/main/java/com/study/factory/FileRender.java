package com.study.factory;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class FileRender implements Render {

    private final static String LINK_MESSAGE_TEMPLATE = "Html report is available on http://localhost:%s/reports/%s %n";
    private final String rootPath;
    private final String port;

    public FileRender(String rootPath, String port) {
        this.rootPath = rootPath + "/reports/";
        this.port = port;
        var folder = new File(this.rootPath);
        if (!folder.exists()) {
            log.info("Folder {} is created: {}", rootPath, folder.mkdir());
        }
    }

    @SneakyThrows
    @Override
    public void render(byte[] data) {
        var file = LocalDateTime.now()
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".html";
        var filePath = rootPath + file;
        System.out.printf(LINK_MESSAGE_TEMPLATE, port, file);
        new FileOutputStream(filePath).write(data);
    }
}
