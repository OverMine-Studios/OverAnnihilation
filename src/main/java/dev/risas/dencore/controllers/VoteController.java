package dev.risas.dencore.controllers;

import dev.risas.dencore.models.vote.VoteGame;
import dev.risas.dencore.models.world.World;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Risas
 * @date 09-06-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class VoteController {

    private final Map<String, VoteGame> votes;

    public VoteController(WorldController worldController) {
        this.votes = new HashMap<>();

        for (World world : worldController.getWorlds()) {
            this.votes.put(world.getName(), new VoteGame(world.getName()));
        }
    }

    public VoteGame getVoteGame(String gameMap) {
        return votes.get(gameMap);
    }

    public VoteGame getVoteGameByPlayer(UUID uuid) {
        return votes.values()
                .stream()
                .filter(voteGame -> voteGame.getPlayers().contains(uuid))
                .findFirst()
                .orElse(null);
    }

    public String getMostVotedGame() {
        return votes.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Comparator.comparingInt(VoteGame::getVoteCount)))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void removeVoteGamePlayer(UUID uuid) {
        VoteGame voteGame = getVoteGameByPlayer(uuid);
        if (voteGame != null) voteGame.removeVote(uuid);
    }
}
