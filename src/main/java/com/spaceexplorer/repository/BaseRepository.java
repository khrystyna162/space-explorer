package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Abstract base class for repositories providing common file I/O operations.
 * Implements JSON-based persistence using Jackson ObjectMapper.
 *
 * @param <T> The type of entity managed by this repository
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseRepository<T> {
    /** Jackson ObjectMapper for JSON serialization/deserialization */
    protected final ObjectMapper mapper;

    /** Path to the JSON file storing the repository data */
    protected final String filePath;

    /** Logger instance for this repository */
    protected final Logger logger;

    /**
     * Creates a new BaseRepository with the specified file path.
     *
     * @param filePath Path to the JSON file for data storage
     */
    protected BaseRepository(String filePath) {
        this.mapper = new ObjectMapper();
        this.filePath = filePath;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Writes data to the repository's JSON file.
     *
     * @param data The object to serialize and save
     * @throws RuntimeException if an I/O error occurs during writing
     */
    protected void writeToFile(Object data) {
        try {
            mapper.writeValue(new File(filePath), data);
            logger.info("Data successfully written to {}", filePath);
        } catch (IOException e) {
            logger.error("Error writing to file: {}", e.getMessage());
            throw new RuntimeException("Error saving data", e);
        }
    }

    /**
     * Reads and deserializes data from the repository's JSON file.
     *
     * @param <R> The type to deserialize the data into
     * @param type The class of the type to deserialize into
     * @return The deserialized object, or null if the file doesn't exist
     * @throws RuntimeException if an I/O error occurs during reading
     */
    protected <R> R readFromFile(Class<R> type) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.warn("File {} does not exist", filePath);
                return null;
            }
            return mapper.readValue(file, type);
        } catch (IOException e) {
            logger.error("Error reading from file: {}", e.getMessage());
            throw new RuntimeException("Error loading data", e);
        }
    }

    /**
     * Reads and deserializes data from the repository's JSON file using a TypeReference.
     * This method is useful for reading collections and generic types.
     *
     * @param <R> The type to deserialize the data into
     * @param typeReference Type reference describing the type to deserialize into
     * @return The deserialized object, or null if the file doesn't exist
     * @throws RuntimeException if an I/O error occurs during reading
     */
    protected <R> R readFromFile(TypeReference<R> typeReference) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.warn("File {} does not exist", filePath);
                return null;
            }
            return mapper.readValue(file, typeReference);
        } catch (IOException e) {
            logger.error("Error reading from file: {}", e.getMessage());
            throw new RuntimeException("Error loading data", e);
        }
    }
}