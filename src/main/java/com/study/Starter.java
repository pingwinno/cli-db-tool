package com.study;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws IOException {
        Properties defaultProps = new Properties();
        defaultProps.load(new FileInputStream("application.properties"));


    }
}
