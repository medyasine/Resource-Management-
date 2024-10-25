package service;

import dao.DAO;
import entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements DAO<Category> {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaDemo");

    @Override
    public Optional<Category> get(Long id) {
        EntityManager em = emf.createEntityManager();
        Category category = null;
        try {
            category = em.find(Category.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close(); // Close the EntityManager
        }
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> getAll() {
        EntityManager em = emf.createEntityManager();
        List<Category> listCategory = new ArrayList<>();
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            listCategory = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close(); // Close the EntityManager
        }
        return listCategory;
    }

    @Override
    public int save(Category category) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
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
    public Optional<Category> update(long id, Category category) {
        EntityManager em = emf.createEntityManager();
        Optional<Category> updatedCategory = Optional.empty();
        try {
            em.getTransaction().begin();
            Optional<Category> existingCategoryOpt = get(id);
            if (existingCategoryOpt.isPresent()) {
                Category existingCategory = existingCategoryOpt.get();
                existingCategory.setTitre(category.getTitre());
                existingCategory.setDescription(category.getDescription());
                em.merge(existingCategory);
                updatedCategory = Optional.of(existingCategory);
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
        return updatedCategory;
    }

    @Override
    public int delete(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Category category = em.find(Category.class, id);
            if (category == null) {
                em.getTransaction().rollback();
                return 0;
            }
            em.remove(category);
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
