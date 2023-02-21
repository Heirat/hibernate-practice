package ru.heirat;

import ru.heirat.app.Application;

import java.io.IOException;

public class Main {
    private static final String DEFAULT_CONFIG_PATH = "config.txt";

    public static void main(String[] args) {
        Application app;
        try {
            if (args.length > 0 && args[0] != null) {
                app = new Application(args[0]);
                System.out.println(args[0]);
            }
            else {
                System.out.println("Укажите путь к конфигу первым аргументом в командной строке");
                System.out.println("Использован путь к конфигу по умолчанию: config.txt.");
                app = new Application(DEFAULT_CONFIG_PATH);
            }
            app.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
