package app;

import controllers.CategoryController;
import controllers.ProductController;
import entity.Category;
import entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CRUDInterface extends JFrame {
    private final JTable productTable;
    private final JTable categoryTable;
    private final DefaultTableModel productTableModel;
    private final DefaultTableModel categoryTableModel;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JComboBox<String> categoryFilter;
    private final JPanel filterPanel;

    // Controller instances
    private final ProductController productController;
    private final CategoryController categoryController;

    public CRUDInterface() {
        super("Resource Management");

        // Initialize controllers
        productController = new ProductController();
        categoryController = new CategoryController();

        // Initialize tables and models
        productTableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Price", "Quantity", "sdr", "Category Id"}, 0);
        categoryTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Description"}, 0);
        productTable = new JTable(productTableModel);
        categoryTable = new JTable(categoryTableModel);

        // Create layout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize category filter components
        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories"); // Add default option
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter by Category: "));
        filterPanel.add(categoryFilter);
        filterPanel.setVisible(false); // Initially hidden

        // Add action listener to category filter
        categoryFilter.addActionListener(e -> {
            String selectedCategory = (String) categoryFilter.getSelectedItem();
            if (selectedCategory != null) {
                if (selectedCategory.equals("All Categories")) {
                    loadProducts();
                } else {
                    List<Product> filteredProducts = productController.getProductsByCategory(selectedCategory);
                    updateProductTable(filteredProducts);
                }
            }
        });

        // Create product panel
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        productPanel.add(createButtonPanel("Product"), BorderLayout.SOUTH);

        // Create category panel
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(new JScrollPane(categoryTable), BorderLayout.CENTER);
        categoryPanel.add(createButtonPanel("Category"), BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(productPanel, "Product");
        mainPanel.add(categoryPanel, "Category");

        // Create navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton productViewButton = new JButton("View Products");
        JButton categoryViewButton = new JButton("View Categories");
        navigationPanel.add(productViewButton);
        navigationPanel.add(categoryViewButton);

        // Create top panel to hold navigation and filter
        JPanel topPanel = new JPanel();
        topPanel.add(navigationPanel);
        topPanel.add(filterPanel);

        // Set up button actions for navigation
        productViewButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Product");
            filterPanel.setVisible(true);
            loadProducts();
            updateCategoryFilter(); // Refresh category filter options
        });

        categoryViewButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Category");
            filterPanel.setVisible(false);
            loadCategories();
        });

        // Add panels to frame
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Window settings
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initial load of categories for the filter
        updateCategoryFilter();
    }

    // New method to update category filter options
    private void updateCategoryFilter() {
        categoryFilter.removeAllItems();
        categoryFilter.addItem("All Categories");
        List<Category> categories = categoryController.getAllCategories();
        for (Category category : categories) {
            categoryFilter.addItem(category.getTitre());
        }
    }

    // New method to update product table with filtered results
    private void updateProductTable(List<Product> products) {
        productTableModel.setRowCount(0);  // Clear existing rows
        for (Product product : products) {
            productTableModel.addRow(new Object[]{
                    product.getId(),
                    product.getDestination(),
                    product.getPrix(),
                    product.getQuantite(),
                    product.getSdr(),
                    product.getCategory() != null ? product.getCategory().getId() : "No Category"
            });
        }
    }

    // Load products into the product table
    private void loadProducts() {
        productTableModel.setRowCount(0);  // Clear existing rows
        List<Product> products = productController.getAllProducts();
        for (Product product : products) {
            productTableModel.addRow(new Object[]{
                    product.getId(),
                    product.getDestination(),
                    product.getPrix(),
                    product.getQuantite(),
                    product.getSdr(),
                    product.getCategory() != null ? product.getCategory().getId() : "No Category"
            });
        }
    }

    private void loadCategories() {
        categoryTableModel.setRowCount(0);
        List<Category> categories = categoryController.getAllCategories();
        for (Category category : categories) {
            categoryTableModel.addRow(new Object[]{
                    category.getId(),
                    category.getTitre(),
                    category.getDescription()
            });
        }
    }

    private JPanel createButtonPanel(String resourceType) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add " + resourceType);
        JButton updateButton = new JButton("Update " + resourceType);
        JButton deleteButton = new JButton("Delete " + resourceType);
        JButton refreshButton = new JButton("Refresh " + resourceType);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Add action listeners to buttons
        if (resourceType.equals("Product")) {
            refreshButton.addActionListener(e -> {
                loadProducts();
                categoryFilter.setSelectedIndex(0);
            });
        } else if (resourceType.equals("Category")) {
            refreshButton.addActionListener(e -> loadCategories());
        }
        addButton.addActionListener(e -> {
            if (resourceType.equals("Product")) {
                // Create input fields for adding a new product
                JTextField descriptionField = new JTextField();
                JTextField priceField = new JTextField();
                JTextField quantityField = new JTextField();
                JTextField sdrField = new JTextField();
                JTextField categoryIdField = new JTextField();

                // Create a panel to hold the input fields
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Price:"));
                panel.add(priceField);
                panel.add(new JLabel("Quantity:"));
                panel.add(quantityField);
                panel.add(new JLabel("Sdr:"));
                panel.add(sdrField);
                panel.add(new JLabel("Category ID:"));
                panel.add(categoryIdField);

                // Show the dialog
                int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    // Retrieve input values
                    String description = descriptionField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    long quantity = Integer.parseInt(quantityField.getText());
                    long sdr = Long.parseLong(sdrField.getText());
                    long categoryId = Long.parseLong(categoryIdField.getText());
                    Category category = categoryController.getCategory(categoryId);
                    // Create a new Product object and add it
                    Product newProduct = new Product(description, price, quantity, sdr, category);
                    productController.saveProduct(newProduct);
                    loadProducts();
                }
            } else {
                // Create input fields for adding a new category
                JTextField nameField = new JTextField();
                JTextField descriptionField = new JTextField();

                // Create a panel to hold the input fields
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);

                // Show the dialog
                int result = JOptionPane.showConfirmDialog(this, panel, "Add Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    // Retrieve input values
                    String name = nameField.getText();
                    String description = descriptionField.getText();

                    // Create a new Category object and add it
                    Category newCategory = new Category(name, description);
                    categoryController.saveCategory(newCategory);
                    loadCategories();
                }
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow;
            if (resourceType.equals("Product")) {
                selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {  // Ensure a row is selected
                    long productId = (long) productTableModel.getValueAt(selectedRow, 0);
                    String description = (String) productTableModel.getValueAt(selectedRow, 1);
                    double price = (double) productTableModel.getValueAt(selectedRow, 2);
                    long quantity = (long) productTableModel.getValueAt(selectedRow, 3);
                    long sdr = (long) productTableModel.getValueAt(selectedRow, 4);
                    long categoryId = (long) productTableModel.getValueAt(selectedRow, 5);

                    // Create input fields for updating the product
                    JTextField descriptionField = new JTextField(description);
                    JTextField priceField = new JTextField(String.valueOf(price));
                    JTextField sdrField = new JTextField(String.valueOf(sdr));
                    JTextField quantityField = new JTextField(String.valueOf(quantity));
                    JTextField categoryIdField = new JTextField(String.valueOf(categoryId));

                    // Create a panel to hold the input fields
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Description:"));
                    panel.add(descriptionField);
                    panel.add(new JLabel("Price:"));
                    panel.add(priceField);
                    panel.add(new JLabel("Quantity:"));
                    panel.add(quantityField);
                    panel.add(new JLabel("Sdr:"));
                    panel.add(sdrField);
                    panel.add(new JLabel("Category ID:"));
                    panel.add(categoryIdField);

                    // Show the dialog
                    int result = JOptionPane.showConfirmDialog(this, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        // Retrieve the updated values
                        String updatedDescription = descriptionField.getText();
                        double updatedPrice = Double.parseDouble(priceField.getText());
                        long updatedQuantity = Integer.parseInt(quantityField.getText());
                        long updatedSdr = Long.parseLong(sdrField.getText());
                        long updatedCategoryId = Long.parseLong(categoryIdField.getText());
                        Category category = categoryController.getCategory(updatedCategoryId);
                        Product updatedProduct = new Product(updatedDescription, updatedPrice, updatedQuantity, updatedSdr, category);
                        productController.updateProduct(productId, updatedProduct);
                        loadProducts();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a product to update.");
                }
            } else {
                selectedRow = categoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    long categoryId = (long) categoryTableModel.getValueAt(selectedRow, 0);
                    String name = (String) categoryTableModel.getValueAt(selectedRow, 1);
                    String description = (String) categoryTableModel.getValueAt(selectedRow, 2);

                    // Create input fields for updating the category
                    JTextField nameField = new JTextField(name);
                    JTextField descriptionField = new JTextField(description);

                    // Create a panel to hold the input fields
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Name:"));
                    panel.add(nameField);
                    panel.add(new JLabel("Description:"));
                    panel.add(descriptionField);

                    // Show the dialog
                    int result = JOptionPane.showConfirmDialog(this, panel, "Update Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        // Retrieve the updated values
                        String updatedName = nameField.getText();
                        String updatedDescription = descriptionField.getText();

                        // Create updated category object
                        Category updatedCategory = new Category(updatedName, updatedDescription);
                        categoryController.updateCategory(categoryId, updatedCategory);
                        loadCategories();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a category to update.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow;
            if (resourceType.equals("Product")) {
                selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    long productId = (long) productTableModel.getValueAt(selectedRow, 0);
                    productController.deleteProduct(productId);
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a product to delete.");
                }
            } else {
                selectedRow = categoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    long categoryId = (long) categoryTableModel.getValueAt(selectedRow, 0);
                    categoryController.deleteCategory(categoryId);
                    loadCategories();
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a category to delete.");
                }
            }
        });


        return buttonPanel;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CRUDInterface view = new CRUDInterface();
            view.setVisible(true);
        });
    }
}
