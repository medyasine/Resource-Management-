package dao;

import entity.Product;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(Long id);        // Use generic type T
    List<T> getAll();                // Use generic type T
    int save(T object);             // Use generic type T
    Optional<T> update(long id, T object);  // Generic return type T
    int delete(long id);
}

