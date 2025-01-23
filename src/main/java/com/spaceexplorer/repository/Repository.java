package main.java.com.spaceexplorer.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void save(T entity);
    Optional<T> findById(String id);
    List<T> findAll();
    void delete(String id);
    void update(T entity);
}
