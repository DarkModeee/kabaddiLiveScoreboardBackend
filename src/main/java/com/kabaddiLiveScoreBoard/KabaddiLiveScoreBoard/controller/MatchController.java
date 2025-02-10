package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.controller;

import com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.model.ScoreboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/scoreboard")
@CrossOrigin(origins = "*")
@EnableScheduling // ✅ Enables scheduling for the timer update
public class MatchController {

    private final ScoreboardData scoreboardData = new ScoreboardData();
    private int matchTime = 1200; // Default 20 minutes (1200 seconds)
    private boolean timerRunning = false;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/set-teams")
    public Map<String, Object> setTeams(@RequestParam String team1, @RequestParam String team2) {
        Map<String, String> teams = new HashMap<>();
        teams.put("team1", team1);
        teams.put("team2", team2);

        scoreboardData.setTeams(teams);
        scoreboardData.resetScores();

        Map<String, boolean[]> players = new HashMap<>();
        players.put(team1, new boolean[7]);
        players.put(team2, new boolean[7]);
        scoreboardData.setPlayers(players);

        broadcastUpdate();
        return getCurrentData();
    }

    @PostMapping("/update-score")
    public Map<String, Integer> updateScore(@RequestParam String team, @RequestParam int delta) {
        Map<String, Integer> scores = scoreboardData.getScores();
        scores.put(team, scores.getOrDefault(team, 0) + delta);
        broadcastUpdate();
        return scores;
    }

    @PostMapping("/set-timer")
    public Map<String, Object> setTimer(@RequestParam int minutes) {
        matchTime = minutes * 60;
        broadcastUpdate();
        return getCurrentData();
    }

    @PostMapping("/toggle-timer")
    public Map<String, Object> toggleTimer() {
        timerRunning = !timerRunning;
        broadcastUpdate();
        return getCurrentData();
    }

    @PostMapping("/toggle-player")
    public Map<String, Object> togglePlayer(@RequestParam String team, @RequestParam int index) {
        Map<String, boolean[]> players = scoreboardData.getPlayers();

        if (players.containsKey(team)) {
            boolean[] teamPlayers = players.get(team);

            if (index >= 0 && index < teamPlayers.length) {
                // Toggle player's status (in/out)
                teamPlayers[index] = !teamPlayers[index];

              /*  // Ensure the first player is always active
                if (teamPlayers.length > 0) {
                    teamPlayers[0] = false; // Make the first player active
                }*/

                // Check if all players are out
                boolean allOut = true;
                for (boolean player : teamPlayers) {
                    if (!player) { // If at least one player is still in, break
                        allOut = false;
                        break;
                    }
                }

                // If all players are out, reset them all back in
                if (allOut) {
                    for (int i = 0; i < teamPlayers.length; i++) {
                        teamPlayers[i] = false; // Reset all players to "in"
                    }
                }
            }
        }

        broadcastUpdate(); // Send updated data to WebSocket clients
        return getCurrentData();
    }



    @GetMapping("/get-data")
    public Map<String, Object> getData() {
        return getCurrentData();
    }

    @Scheduled(fixedRate = 1000)
    public void updateTimer() {
        if (timerRunning && matchTime > 0) {
            matchTime--;
            broadcastUpdate();
        }
    }

    private void broadcastUpdate() {
        System.out.println("Broadcasting Update: " + getCurrentData());
        messagingTemplate.convertAndSend("/topic/scoreboard", getCurrentData());
    }

    @MessageMapping("/update-action")
    @SendTo("/topic/scoreboard")
    public Map<String, Object> processWebSocketAction(String action, String team, int delta, String team1, String team2, int playerIndex, int minutes) {
        switch (action) {
            case "update-score":
                updateScore(team, delta);
                break;
            case "set-teams":
                setTeams(team1, team2);
                break;
            case "toggle-player":
                togglePlayer(team, playerIndex);
                break;
            case "set-timer":
                setTimer(minutes);
                break;
            case "toggle-timer":
                toggleTimer();
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
        return getCurrentData();
    }

    private Map<String, Object> getCurrentData() {
        Map<String, Object> data = new HashMap<>();
        data.put("teams", scoreboardData.getTeams());
        data.put("scores", scoreboardData.getScores());

        Map<String, List<Boolean>> playersList = new HashMap<>();
        for (Map.Entry<String, boolean[]> entry : scoreboardData.getPlayers().entrySet()) {
            List<Boolean> playerStates = new ArrayList<>();
            for (boolean state : entry.getValue()) {
                playerStates.add(state);
            }
            playersList.put(entry.getKey(), playerStates);
        }

        data.put("players", playersList);
        data.put("matchTime", matchTime);
        data.put("timerRunning", timerRunning);

        System.out.println("Broadcasting Update: " + data);
        return data;
    }
}
