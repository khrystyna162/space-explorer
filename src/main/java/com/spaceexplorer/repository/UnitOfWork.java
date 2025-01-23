package main.java.com.spaceexplorer.repository;

public class UnitOfWork {
    private GameRepository gameRepository;
    private boolean complete = false;

    public UnitOfWork(String filePath) {
        this.gameRepository = new GameRepository(filePath);
    }

    public void commit() {
        if (!complete) {
            complete = true;
        }
    }

    public void rollback() {
        if (!complete) {
            complete = true;
        }
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public boolean isComplete() {
        return complete;
    }
}