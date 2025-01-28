package main.java.com.spaceexplorer.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface defining standard CRUD operations.
 * Provides a common contract for all repositories in the application.
 *
 * @param <T> The type of entity managed by the repository
 * @param <ID> The type of the entity's identifier
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public interface Repository<T, ID> {
    /**
     * Saves an entity to the repository.
     *
     * @param entity The entity to save
     * @throws IllegalArgumentException if the entity is invalid
     */
    void save(T entity);

    /**
     * Finds an entity by its identifier.
     *
     * @param id The identifier of the entity to find
     * @return Optional containing the entity if found
     */
    Optional<T> findById(ID id);

    /**
     * Returns all entities in the repository.
     *
     * @return List of all entities
     */
    List<T> findAll();

    /**
     * Deletes an entity by its identifier.
     *
     * @param id The identifier of the entity to delete
     */
    void delete(ID id);

    /**
     * Updates an existing entity.
     *
     * @param id The identifier of the entity to update
     * @param entity The new entity data
     * @throws IllegalArgumentException if the entity is not found or invalid
     */
    void update(ID id, T entity);

    /**
     * Checks if an entity exists by its identifier.
     *
     * @param id The identifier to check
     * @return true if the entity exists, false otherwise
     */
    boolean exists(ID id);
}