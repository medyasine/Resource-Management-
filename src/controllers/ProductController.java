package controllers;

import entity.Category;
import entity.Product;
import jakarta.persistence.EntityNotFoundException;
import service.ProductDAOImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    private final ProductDAOImpl productDAO;
    public ProductController()
    {
        this.productDAO = new ProductDAOImpl();
    }
    public Product getProduct(long id)
    {
        return productDAO.get(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }
    public List<Product> getAllProducts()
    {
        return productDAO.getAll();
    }
    public List<Product> getProductsByCategory(String category)
    {
        return productDAO.getAll().stream().filter(e -> e.getCategory().getTitre().equals(category)).collect(Collectors.toList());
    }
    public int saveProduct(Product p)
    {
        return productDAO.save(p);
    }
    public Product updateProduct(long id, Product category)
    {
        return productDAO.update(id, category).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }
    public void deleteProduct(long id) {
        if (productDAO.delete(id) == 0) {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }
}
