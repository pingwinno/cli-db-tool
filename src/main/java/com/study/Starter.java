package com.study;

import com.study.db.ConnectionFactory;
import com.study.db.QueryProcessor;
import com.study.factory.ConsoleRender;
import com.study.factory.FileRender;
import com.study.render.AsciiTableGenerator;
import com.study.render.HTMLTableGenerator;
import com.study.service.QueryProcessingService;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

public class Starter {

    private static final String URL_PROPERTY = "db.url";
    private static final String USER_PROPERTY = "db.user";
    private static final String PASSWORD_PROPERTY = "db.password";
    private static final String PORT_PROPERTY = "server.port";
    private static final String REPORT_FOLDER = "report";
    private static final Properties fileProperties = new Properties();

    public static void main(String[] args) throws IOException {
        fileProperties.load(new FileInputStream("application.properties"));
        var connectionFactory = new ConnectionFactory(getProperty(URL_PROPERTY), getProperty(USER_PROPERTY),
                getProperty(PASSWORD_PROPERTY));
        var queryProcessor = new QueryProcessor(connectionFactory);

        var queryProcessingService = new QueryProcessingService(queryProcessor,
                new HTMLTableGenerator(new FileRender(REPORT_FOLDER, getProperty(PORT_PROPERTY))),
                new AsciiTableGenerator(new ConsoleRender()));

        Scanner in = new Scanner(System.in);
        new Thread(() -> startServer(Integer.parseInt(getProperty(PORT_PROPERTY)))).start();
        while (true) {
            System.out.printf("%n Type query %n");
            queryProcessingService.process(in.nextLine());
        }
    }

    private static String getProperty(String property) {
        return Optional.ofNullable(System.getProperty(property))
                       .or(() -> Optional.ofNullable(fileProperties.getProperty(property)))
                       .orElseThrow(() -> new IllegalArgumentException(
                               "Property " + property + " is not set. %n Please pass property to args or check application.properties"));
    }

    @SneakyThrows
    private static void startServer(int port) {
        new Server(port, Starter.REPORT_FOLDER).start();
    }
}
