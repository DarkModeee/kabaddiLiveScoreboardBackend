package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/kabaddi")
@CrossOrigin
public class KabaddiRestController {
    private final Map<String, Object> gameState = new ConcurrentHashMap<>();

    @PostMapping("/update")
    public Map<String, Object> updateGameState(@RequestBody Map<String, Object> data) {
        gameState.putAll(data);
        return gameState;
    }

    @GetMapping("/state")
    public Map<String, Object> getGameState() {
        return gameState;
    }
}