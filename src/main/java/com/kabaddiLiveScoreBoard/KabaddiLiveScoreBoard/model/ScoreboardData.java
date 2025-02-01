package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.model;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardData {
    private Map<String, Integer> scores;      // Stores team scores
    private Map<String, String> teams;        // Stores team names
    private Map<String, boolean[]> players;   // Stores player statuses (active/inactive)

    // Default constructor
    public ScoreboardData() {
        this.scores = new HashMap<>();
        this.teams = new HashMap<>();
        this.players = new HashMap<>();
    }

    // Parameterized constructor (optional)
    public ScoreboardData(Map<String, String> teams) {
        this.teams = teams != null ? new HashMap<>(teams) : new HashMap<>();
        this.scores = new HashMap<>();
        this.players = new HashMap<>();
        resetScores();
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = (scores != null) ? new HashMap<>(scores) : new HashMap<>();
    }

    public Map<String, String> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, String> teams) {
        this.teams = (teams != null) ? new HashMap<>(teams) : new HashMap<>();
    }

    public Map<String, boolean[]> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, boolean[]> players) {
        this.players = (players != null) ? new HashMap<>(players) : new HashMap<>();
    }

    /**
     * Resets the scores to 0 for each team.
     */
    public void resetScores() {
        scores.clear(); // Ensure old scores are removed
        for (String team : teams.values()) {
            scores.put(team, 0);
        }
    }

    /**
     * Initializes player statuses to inactive (false).
     */
    public void initializePlayers() {
        for (String team : teams.values()) {
            players.put(team, new boolean[7]); // Assuming 7 players per team
        }
    }

    /**
     * Toggles the status of a player (active/inactive).
     * @param team The team to toggle the player for
     * @param index The index of the player to toggle
     */
    public void togglePlayer(String team, int index) {
        if (players.containsKey(team)) {
            boolean[] teamPlayers = players.get(team);
            if (index >= 0 && index < teamPlayers.length) {
                teamPlayers[index] = !teamPlayers[index]; // Toggle player status
            }
        }
    }
}
