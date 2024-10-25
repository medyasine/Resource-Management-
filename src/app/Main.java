package app;

import entity.Category;
import entity.Product;
import service.CategoryDAOImpl;
import service.ProductDAOImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Initialize DAOs
        CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
        ProductDAOImpl productDAO = new ProductDAOImpl();

        // Create and save Categories
        Category electronics = new Category("All electronic items", "Electronics");
        Category groceries = new Category("All groceries", "Groceries");
        categoryDAO.save(electronics);
        categoryDAO.save(groceries);

        // Fetch all categories
        List<Category> allCategories = categoryDAO.getAll();

        // Print all categories using Streams
        System.out.println("All Categories:");
        allCategories.forEach(category ->
                System.out.println(category.getTitre() + ": " + category.getDescription())
        );

        // Create and save Products
        Product phone = new Product("Phone", 799.99, 100, 1L, electronics);
        Product tv = new Product("Television", 1499.99, 50, 2L, electronics);
        Product apple = new Product("Apple", 0.5, 1000, 3L, groceries);
        productDAO.save(phone);
        productDAO.save(tv);
        productDAO.save(apple);

        // Fetch all products
        List<Product> allProducts = productDAO.getAll();


        // Filter products by category using streams
        System.out.println("\nProducts in Electronics category:");
        List<Product> listP = allProducts.stream().filter(e -> e.getCategory().getTitre() == "Electronics").collect(Collectors.toList());
        listP.forEach(System.out::println);
        // Fetch a single product by ID
        System.out.println("\nProduct of id 1 :");
        Optional<Product> product = productDAO.get(1L);
        product.ifPresent(System.out::println);
        // Update a product
        allProducts.stream().filter(e -> e.getCategory().getTitre() == "Electronics").forEach(e -> {
            e.setCategory(groceries);
            System.out.println(productDAO.update(e.getId(), e));
        });
        // Delete a product (assuming product ID 2 exists)
        // Example using map to transform product names
    }
}
