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
        match.setTeamAName(teamAName);
        match.setTeamBName(teamBName);
        match.setTeamAScore(0); // Reset scores
        match.setTeamBScore(0); // Reset scores
        matchRepository.save(match);
        broadcastMatchUpdate(match);
    }

    public void updateScore(String team, int score) {
        Match match = getActiveMatch();
        if (team.equalsIgnoreCase("teamA")) {
            match.setTeamAScore(score);
        } else if (team.equalsIgnoreCase("teamB")) {
            match.setTeamBScore(score);
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
        messagingTemplate.convertAndSend("/topic/match-updates", new MatchDTO(
                match.getTeamAName(),
                match.getTeamAScore(),
                match.getTeamBName(),
                match.getTeamBScore(),
                match.getTimer()
        ));
    }

    public MatchDTO getMatchDetails(Long matchId) {
        Match match = getActiveMatch();
        return new MatchDTO(
                match.getTeamAName(),
                match.getTeamAScore(),
                match.getTeamBName(),
                match.getTeamBScore(),
                match.getTimer()
        );
    }
}
