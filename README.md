# Resource Management CRUD Application

A Java Swing application for managing products and categories, built using a Card Layout for navigation and a database-based CRUD (Create, Read, Update, Delete) interface for handling resources.

## Features

- **CRUD Operations**: Add, update, delete, and view products and categories.
- **Filter Products**: View products by category.
- **Swing GUI**: User-friendly interface with a Card Layout for switching between products and categories.
- **MVC Pattern**: Separates UI, business logic, and database operations.

## Project Structure

- **app**: Contains the main `CRUDInterface` class that launches the GUI.
- **controllers**: Handles the business logic for products and categories.
  - `ProductController`: Manages products.
  - `CategoryController`: Manages categories.
- **entity**: Defines `Product` and `Category` entities with JPA annotations for ORM (Object-Relational Mapping).
- **service**: Manages database operations using DAO (Data Access Object) pattern for both entities.

## Requirements

- Java 8 or higher
- Swing (included in Java)
- JPA (Jakarta Persistence API)
- Database (any supported JPA provider)

## How to Run

1. **Clone the Repository**:
    ```bash
    git clone <repository-url>
    cd <repository-folder>
    ```

2. **Set Up the Database**:
   - Ensure you have a compatible database.
   - Configure your database connection in `persistence.xml`.

3. **Compile and Run**:
   - Compile the project:
     ```bash
     javac -d bin src/app/*.java src/controllers/*.java src/entity/*.java src/service/*.java
     ```
   - Run the application:
     ```bash
     java -cp bin app.CRUDInterface
     ```

## Usage

- Launch the application to access the **Product** and **Category** management sections.
- **Add/Edit/Delete** products and categories.
- **Filter Products**: Use the dropdown to filter products by category.

## Notes

- Ensure the database is set up and running before launching the application.
- Modify the `CategoryDAOImpl` and `ProductDAOImpl` classes as needed to support your database.

## Future Improvements

- Add additional validation for inputs.
- Implement pagination for large datasets.
- Improve error handling for database operations.

---

### License

This project is licensed under the MIT License. See the LICENSE file for more details.
