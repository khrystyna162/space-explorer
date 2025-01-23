package main.java.com.spaceexplorer.repository;

import main.java.com.spaceexplorer.model.Player;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filters {
    public static List<Player> filterByHealth(List<Player> players, int minHealth) {
        return filterBy(players, p -> p.getHealth() >= minHealth);
    }

    public static List<Player> filterBySector(List<Player> players, String sector) {
        return filterBy(players, p -> p.getCurrentSector().equals(sector));
    }

    private static List<Player> filterBy(List<Player> players, Predicate<Player> predicate) {
        return players.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
