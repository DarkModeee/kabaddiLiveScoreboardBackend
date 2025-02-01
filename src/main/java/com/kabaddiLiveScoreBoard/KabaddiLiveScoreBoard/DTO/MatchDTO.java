package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.DTO;



public class MatchDTO {

    private String teamAName;
    private int teamAScore;
    private String teamBName;
    private int teamBScore;
    private int timer; // Remaining time in seconds

    // Constructors
    public MatchDTO(String teamAName, int teamAScore, String teamBName, int teamBScore, int timer) {
        this.teamAName = teamAName;
        this.teamAScore = teamAScore;
        this.teamBName = teamBName;
        this.teamBScore = teamBScore;
        this.timer = timer;
    }

    // Getters and Setters
    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(int teamAScore) {
        this.teamAScore = teamAScore;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public int getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(int teamBScore) {
        this.teamBScore = teamBScore;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }


}

