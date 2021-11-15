package com.study.render;

import com.study.factory.Render;

import java.nio.charset.Charset;

public class ByteArrayRender implements Render {
    private String string;

    @Override
    public void render(byte[] data) {
        string = new String(data, Charset.defaultCharset());
    }

    public String getString() {
        return string;
    }
}