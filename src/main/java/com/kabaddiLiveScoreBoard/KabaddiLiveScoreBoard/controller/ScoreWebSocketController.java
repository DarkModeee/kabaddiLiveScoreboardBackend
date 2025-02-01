package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ScoreWebSocketController {

    @Autowired
    private MatchController matchController; // Inject MatchController to update scores and game status

    /**
     * This method listens to incoming WebSocket messages and processes them.
     * It updates the match state based on the action specified in the message.
     */
    @MessageMapping("/websocket/action")  // Ensure this is unique
    @SendTo("/topic/scoreboard")
    public Map<String, Object> updateAction(Map<String, Object> actionData) {
        String action = (String) actionData.get("action");
        String team = (String) actionData.get("team");
        int delta = (Integer) actionData.get("delta");
        String team1 = (String) actionData.get("team1");
        String team2 = (String) actionData.get("team2");
        int playerIndex = (Integer) actionData.get("playerIndex");
        int minutes = (Integer) actionData.get("minutes");

        // Process the action received from the WebSocket message
        switch (action) {
            case "update-score":
                matchController.updateScore(team, delta);
                break;
            case "set-teams":
                matchController.setTeams(team1, team2);
                break;
            case "toggle-player":
                matchController.togglePlayer(team, playerIndex);
                break;
            case "set-timer":
                matchController.setTimer(minutes);
                break;
            case "toggle-timer":
                matchController.toggleTimer();
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }

        // Return the updated scoreboard data after processing the action
        System.out.println("WebSocket Update Action: " + action);
        return matchController.getData();
    }
}

