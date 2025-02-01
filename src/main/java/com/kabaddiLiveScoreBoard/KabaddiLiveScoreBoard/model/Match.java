package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.model;

import jakarta.persistence.*;
@Entity
@Table(name = "match_game")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int timer; // Timer in seconds

    @OneToOne(cascade = CascadeType.ALL)
    private TeamsDetails teamA;

    @OneToOne(cascade = CascadeType.ALL)
    private TeamsDetails teamB;


    private Boolean active;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public TeamsDetails getTeamA() {
        return teamA;
    }

    public void setTeamA(TeamsDetails teamA) {
        this.teamA = teamA;
    }

    public TeamsDetails getTeamB() {
        return teamB;
    }

    public void setTeamB(TeamsDetails teamB) {
        this.teamB = teamB;
    }


}
