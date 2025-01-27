package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public abstract class BaseRepository<T> {
    protected final ObjectMapper mapper;
    protected final String filePath;
    protected final Logger logger;

    protected BaseRepository(String filePath) {
        this.mapper = new ObjectMapper();
        this.filePath = filePath;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    protected void writeToFile(Object data) {
        try {
            mapper.writeValue(new File(filePath), data);
            logger.info("Data successfully written to {}", filePath);
        } catch (IOException e) {
            logger.error("Error writing to file: {}", e.getMessage());
            throw new RuntimeException("Error saving data", e);
        }
    }

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
