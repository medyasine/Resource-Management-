package service;

import dao.DAO;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements DAO<Product> {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaDemo");

    @Override
    public Optional<Product> get(Long id) {
        EntityManager em = emf.createEntityManager();
        Product product = null;
        try {
            product = em.find(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close(); // Close the EntityManager
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAll() {
        EntityManager em = emf.createEntityManager();
        List<Product> listProduct = new ArrayList<>();
        try {
            listProduct = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close(); // Close the EntityManager
        }
        return listProduct;
    }

    @Override
    public int save(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close(); // Close the EntityManager
        }
        return 0;
    }

    @Override
    public Optional<Product> update(long id, Product product) {
        EntityManager em = emf.createEntityManager();
        Optional<Product> updatedProduct = Optional.empty();
        try {
            em.getTransaction().begin();
            Optional<Product> existingProductOpt = get(id);
            if (existingProductOpt.isPresent()) {
                Product existingProduct = existingProductOpt.get();
                existingProduct.setPrix(product.getPrix());
                existingProduct.setDestination(product.getDestination());
                existingProduct.setSdr(product.getSdr());
                existingProduct.setQuantite(product.getQuantite());
                existingProduct.setCategory(product.getCategory());
                em.merge(existingProduct);
                updatedProduct = Optional.of(existingProduct);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close(); // Close the EntityManager
        }
        return updatedProduct;
    }
    @Override
    public int delete(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            if (product == null) {
                em.getTransaction().rollback();
                return 0;
            }
            em.remove(product);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        return 0;
    }

}
