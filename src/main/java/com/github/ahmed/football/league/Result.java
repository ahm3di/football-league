package com.github.ahmed.football.league;


import java.io.Serializable;

/**
 *
 * @author Ahmed
 */
public class Result implements Serializable{
    
    private String homeTeam;
    private int homeScore;
    private String awayTeam;
    private int awayScore;

    public Result(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }


    
}
