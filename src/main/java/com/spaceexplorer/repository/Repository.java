package main.java.com.spaceexplorer.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void delete(ID id);
    void update(ID id, T entity);
    boolean exists(ID id);
}
