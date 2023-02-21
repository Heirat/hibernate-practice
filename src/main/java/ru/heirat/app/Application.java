package ru.heirat.app;

import ru.heirat.entity.Product;
import ru.heirat.util.HibernateUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Application {
    private final DataHandler dataHandler;

    public Application(String configPath) throws IOException {
        dataHandler = new DataHandler(configPath);
    }

    public void start() {
        ActionChoice userChoice;
        do {
            userChoice = getUserChoice();
            executeAction(userChoice);
        } while (userChoice != ActionChoice.EXIT);
        HibernateUtil.shutdown();
    }

    private void executeAction(ActionChoice choice) {
        choice.action.accept(this);
    }

    public void addAction() {
        Product newProduct = readNewProduct();
        if (newProduct != null) {
            if (dataHandler.add(newProduct)) {
                System.out.println("Добавление успешно: " + newProduct);
            }
            else {
                System.out.println("Не удалось добавить");
            }
        }
    }

    public void exitAction() {
        System.out.println("Завершение работы.");
    }

    public void removeAction() {
        System.out.println("Введите название товара для удаления");
        readName().ifPresent(name -> {
            if (dataHandler.remove(name)) {
                System.out.println("Удаление успешно.");
            } else {
                System.out.println("Товар для удаления не найден.");
            }
        });
    }

    public void showAction() {
        System.out.println("Список товаров:");
        for (Product product : dataHandler.getAll()) {
            System.out.println(product);
        }
    }

    public void findAction() {
        System.out.println("Введите название товара для поиска");
        readName().ifPresent(name -> {
            Optional<Product> foundProduct = dataHandler.find(name);
            if (foundProduct.isPresent()) {
                System.out.println("Товар найден:");
                System.out.println(foundProduct.get());
            }
            else {
                System.out.println("Товар не найден.");
            }
        });
    }

    private Product readNewProduct() {
        try {
            System.out.println("Введите название товара для добавления:");
            String name = readName().orElseThrow(IOException::new);

            if (!dataHandler.canAddProduct(name)) {
                System.out.println("Данное название запрещено для добавления");
                throw new IOException();
            }

            System.out.println("Введите цену:");
            Double price = readPrice().orElseThrow(IOException::new);

            System.out.println("Введите вес:");
            Double weight = readWeight().orElseThrow(IOException::new);

            return Product.builder()
                    .name(name)
                    .price(price)
                    .weight(weight)
                    .build();
        }
        catch (IOException e) {
            return null;
        }
    }

    private static Optional<String> readName() {
        Scanner in = new Scanner(System.in);
        if (in.hasNext())
            return Optional.of(in.nextLine());
        else {
            System.out.println("Некорректный ввод");
            return Optional.empty();
        }
    }


    private static Optional<Double> readPrice() {
        Scanner in = new Scanner(System.in);
        if (in.hasNextDouble()) {
            return Optional.of(in.nextDouble());
        }
        else {
            System.out.println("Некорректный ввод");
            return Optional.empty();
        }
    }

    private static Optional<Double> readWeight() {
        Scanner in = new Scanner(System.in);
        if (in.hasNextDouble()) {
            return Optional.of(in.nextDouble());
        }
        else {
            System.out.println("Некорректный ввод");
            return Optional.empty();
        }
    }

    private static ActionChoice getUserChoice() {
        System.out.println("Доступные действия:");
        System.out.println("\t0. Добавить товар");
        System.out.println("\t1. Удалить товар");
        System.out.println("\t2. Вывести список товаров");
        System.out.println("\t3. Найти товар по наименованию");
        System.out.println("\t4. Выйти");

        // Возвращает элемент перечисления с индексом choiceNum
        return ActionChoice.values()[getUserChoiceNum()];
    }

    private static Integer getUserChoiceNum() {
        Scanner in = new Scanner(System.in);
        Integer choiceNum = null;

        while (choiceNum == null) {
            System.out.println("Введите номер действия: ");
            if (in.hasNextInt()) {
                choiceNum = in.nextInt();
                if ((choiceNum < 0) || (choiceNum > 4))
                    choiceNum = null;
            } else {
                in.next();
            }
        }
        return choiceNum;
    }
}

