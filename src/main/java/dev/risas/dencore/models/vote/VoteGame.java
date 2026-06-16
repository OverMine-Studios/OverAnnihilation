package dev.risas.dencore.models.vote;

import lombok.Getter;

import java.util.*;

/**
 * @author Risas
 * @date 09-06-2025
 * @discord https://risas.me/discord
 */

@Getter
public class VoteGame {

    private final String gameMap;
    private final Set<UUID> players;

    public VoteGame(String gameMap) {
        this.gameMap = gameMap;
        this.players = new HashSet<>();
    }

    public boolean hasVoted(String gameMap, UUID uuid) {
        return this.gameMap.equals(gameMap) && players.contains(uuid);
    }

    public void addVote(UUID uuid) {
        players.add(uuid);
    }

    public void removeVote(UUID uuid) {
        players.remove(uuid);
    }

    public int getVoteCount() {
        return players.size();
    }
}
