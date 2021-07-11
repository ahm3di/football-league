package com.github.ahmed.football.league;


import java.io.Serializable;

/**
 *
 * @author Ahmed
 */

public class Team implements Serializable{
    

    private String name;
    private int MP;
    private int wins;
    private int draws;
    private int losses;
    private int GF;
    private int GA;
    private int GD;
    private int points;
    
    public Team(){
        
        name = "";
        MP = 0;
        wins = 0;
        draws = 0;
        losses = 0;
        GF = 0;
        GA = 0;
        GD = 0;
        points = 0;
        
    }
    
    public Team (String nameIn, int MPIn, int winsIn, int drawsIn, int lossesIn, int GFIn, int GAIn, int GDIn, int pointsIn)
    {
        name = nameIn;
        MP = MPIn;
        wins = winsIn;
        draws = drawsIn;
        losses = lossesIn;
        GF = GDIn;
        GA = GAIn;
        GD = GDIn;
        points = pointsIn;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getMP()
    {
        MP = wins+losses+draws; //matches played calcualted by adding wins,losses and draws
        return MP;
    }
    
    public int getWins()
    {
        return wins;
    }
    
    public int getDraws()
    {
        return draws;
    }
    
    public int getLosses()
    {
        return losses;
    }
    
    public int getGF()
    {
        return GF;
    }
    
    public int getGA()
    {
        return GA;
    }
    
    public int getGD()
    {
        GD = GF-GA; //The goal difference is calcualted by removing 'goals against' from 'goals for' 
        return GD;
    }
    
    public int getPoints()
    {
        points = ((wins*3) + (draws*1)); //points calcualted - 3 points per win, 1 point per draw
        return points;
    }
    
    public void setName(String nameIn)
    {
        name = nameIn;
    }
    
    public void setMP(int MPIn)
    {
        MP = MPIn;
    }
    
    public void setWins(int winsIn)
    {
        //wins is set by winsIn to the previous value
        wins = wins + winsIn; 
    }
    
    public void setDraws(int drawsIn)
    {
        //wins is set by drawsIn to the previous value
        draws = draws + drawsIn; 
    }
    
    public void setLosses(int lossesIn)
    {
        //wins is set by lossesIn to the previous value
        losses = losses + lossesIn; 
    }
    
    public void setGF(int GFIn)
    {
        GF = GFIn;
    }
    
    public void setGA(int GAIn)
    {
        GA = GAIn;
    }
    
    public void setGD(int GDIn)
    {
        GD = GDIn;
    }
    
    public void setPoints(int pointsIn)
    {
        points = pointsIn;
    }
    
    //override equals to accept an object, compare that object to team name and return boolean variable 'result'
    //This comes in use when the .contains method is called in the TeamGUI class
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Team teams = (Team) object;
            if (this.name.equalsIgnoreCase(teams.getName())) {
                result = true;
            }
        }
        return result;
}
    
    
}
