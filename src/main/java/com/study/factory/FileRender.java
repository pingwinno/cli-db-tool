package com.study.factory;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.time.LocalDateTime;

public class FileRender implements Render {

    private String rootPath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @SneakyThrows
    @Override
    public void render(byte[] data) {
        var filePath = rootPath + "/" + LocalDateTime.now();

        new FileOutputStream(rootPath).write(data);
    }
}
