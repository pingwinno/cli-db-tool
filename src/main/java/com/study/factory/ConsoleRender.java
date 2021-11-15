package com.study.factory;

import lombok.SneakyThrows;

public class ConsoleRender implements Render {
    @SneakyThrows
    @Override
    public void render(byte[] data) {
        System.out.write(data);
    }
}
