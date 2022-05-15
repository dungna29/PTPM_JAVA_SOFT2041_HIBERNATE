/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;


import DomainModels.Product;
import Utilities.HibernateUtil;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
/**
 *
 * @author PTPM
 */
public class ProductRepository implements IProductRepository {

    @Override
    public List<Product> findAll(int position, int pageSize) {
        List<Product> products;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Product p";
            TypedQuery<Product> query = session.createQuery(hql, Product.class);
            query.setFirstResult(position);
            query.setMaxResults(pageSize);
            products = query.getResultList();
        }
        return products;
    }

    @Override
    public Product findById(long id) {
        Product product;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Product p WHERE p.id = :id";
            TypedQuery<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("id", id);
            product = query.getSingleResult();
        }
        return product;
    }

    @Override
    public Product save(Product product) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction trans = session.getTransaction();
            trans.begin();
            try {
                session.saveOrUpdate(product);
                trans.commit();
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
                product = null;
            }
        } finally {
            return product;
        }
    }

    @Override
    public long delete(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction trans = session.getTransaction();
            trans.begin();
            try {
                String hql = "DELETE Product p WHERE p.id = :id";
                Query query = session.createQuery(hql);
                query.setParameter("id", id);
                int result = query.executeUpdate();
                if (result == 0) {
                    id = 0;
                }
                trans.commit();
            } catch (Exception e) {
                id = -1;
            }
           
        }
        return id;
    }

    @Override
    public long totalCount() {
        long total = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String statement = "SELECT COUNT(p.id) FROM Product p";
            TypedQuery<Long> query = session.createQuery(statement, Long.class);
            total = query.getSingleResult();
        }
        return total;
    }

}
