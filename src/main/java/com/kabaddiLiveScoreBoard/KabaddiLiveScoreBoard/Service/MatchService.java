package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.Service;

import com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.DTO.MatchDTO;
import com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.Repository.MatchRepository;
import com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Match getActiveMatch() {
        Match match = matchRepository.findActiveMatch();
        if (match == null) {
            throw new RuntimeException("No active match found!");
        }
        return match;
    }

    public void setTeamNames(String teamAName, String teamBName) {
        Match match = getActiveMatch();
        match.getTeamA().setName(teamAName);
        match.getTeamB().setName(teamBName);
        matchRepository.save(match);
        broadcastMatchUpdate(match);
    }

    public void updateScore(String team, int score) {
        Match match = getActiveMatch();
        if (team.equalsIgnoreCase("teamA")) {
            match.getTeamA().setScore(score);
        } else if (team.equalsIgnoreCase("teamB")) {
            match.getTeamB().setScore(score);
        } else {
            throw new IllegalArgumentException("Invalid team name");
        }
        matchRepository.save(match);
        broadcastMatchUpdate(match);
    }

    public void setTimer(int seconds) {
        Match match = getActiveMatch();
        match.setTimer(seconds);
        matchRepository.save(match);
        broadcastMatchUpdate(match);
    }

    public void broadcastMatchUpdate(Match match) {
        // Send match updates to clients via WebSocket
        messagingTemplate.convertAndSend("/topic/match-updates", new MatchDTO(
                match.getTeamA().getName(),
                match.getTeamA().getScore(),
                match.getTeamB().getName(),
                match.getTeamB().getScore(),
                match.getTimer()
        ));
    }

    public MatchDTO getMatchDetails(Long matchId) {
        Match match = getActiveMatch(); // Retrieve match using matchId
        return new MatchDTO(
                match.getTeamA().getName(),
                match.getTeamA().getScore(),
                match.getTeamB().getName(),
                match.getTeamB().getScore(),
                match.getTimer()
        );
    }
}


