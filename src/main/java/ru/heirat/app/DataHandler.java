package ru.heirat.app;

import ru.heirat.dao.ProductDAO;
import ru.heirat.entity.Product;
import ru.heirat.service.ProductService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataHandler {
    private final ProductDAO products;
    private final ArrayList<String> filterStrings = new ArrayList<>();

    public DataHandler(String configPath) throws IOException {
        readFromConfigFile(configPath);
        products = new ProductService();
    }

    // Бросает исключение, если не найден файл или ошибка во время чтения
    public void readFromConfigFile(String configPath) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(configPath))) {
            String filterLine;
            while ((filterLine = in.readLine()) != null) {
                filterStrings.add(filterLine);
            }
        }
    }

    public List<Product> getAll() {
        return products.getAll();
    }

    public Boolean add(Product newProduct) {
        return products.add(newProduct);
    }

    public boolean remove(String name) {
        return products.remove(name);
    }

    public Optional<Product> find(String name) {
        return products.getByName(name);
    }

    public boolean canAddProduct(String name) {
        // Если имя не удовлетворяет никакому фильтру, тогда можем добавить
        return filterStrings.stream().noneMatch(name::matches);
    }
}

