package ru.heirat.dao;

import ru.heirat.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Boolean add(Product product);

    List<Product> getAll();

    Optional<Product> getById(Integer id);

    Optional<Product> getByName(String name);

    void update(Product product);

    Boolean remove(String name);
}
