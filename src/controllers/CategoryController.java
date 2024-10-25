package controllers;

import entity.Category;
import jakarta.persistence.EntityNotFoundException;
import service.CategoryDAOImpl;

import java.util.List;

public class CategoryController {
    private final CategoryDAOImpl categoryDAO;
    public CategoryController()
    {
        this.categoryDAO = new CategoryDAOImpl();
    }
    public Category getCategory(long id)
    {
         return categoryDAO.get(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }
    public List<Category> getAllCategories()
    {
        return categoryDAO.getAll();
    }
    public int saveCategory(Category c)
    {
        return categoryDAO.save(c);
    }
    public Category updateCategory(long id, Category category)
    {
        return categoryDAO.update(id, category).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }
    public void deleteCategory(long id)
    {
        if (categoryDAO.delete(id) == 0) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }    }
}
