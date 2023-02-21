package ru.heirat.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.heirat.dao.ProductDAO;
import ru.heirat.entity.Product;
import ru.heirat.util.SessionUtil;
import java.util.List;
import java.util.Optional;

public class ProductService extends SessionUtil implements ProductDAO {
    @Override
    public Boolean add(Product product){
        try {
            Session session = openTransactionSession();
            session.persist(product);
        }
        catch (PersistenceException e) {
            return false;
        }
        finally {
            closeTransactionSession();
        }
        return true;
    }

    @Override
    public List<Product> getAll(){
        Session session = openTransactionSession();

        Query<Product> query = session.createQuery("from Product", Product.class);
        List<Product> products = query.list();

        closeTransactionSession();

        return products;
    }

    @Override
    public Optional<Product> getById(Integer id) {
        Session session = openTransactionSession();
        Optional<Product> product = Optional.ofNullable(session.byId(Product.class).load(id));
        closeTransactionSession();

        return product;
    }

    @Override
    public Optional<Product> getByName(String name) {
        Session session = openTransactionSession();
        Optional<Product> product = Optional.ofNullable(session.bySimpleNaturalId(Product.class).load(name));
        closeTransactionSession();

        return product;
    }

    @Override
    public void update(Product product) {
        Session session = openTransactionSession();
        session.merge(product);
        closeTransactionSession();
    }

    @Override
    public Boolean remove(String name) {
        Session session = openTransactionSession();

        Boolean updateResult = session.createMutationQuery("delete from Product where name = :name")
                .setParameter("name", name)
                .executeUpdate() > 0;

        closeTransactionSession();

        return updateResult;
    }
}
