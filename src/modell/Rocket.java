/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

public class Rocket {
    
    // FIELDS
    private int x;
    private int y;
    private int rocketSize;
    
    private double speed;
    private int playerID;
    
    // CONSTRUCTOR
    public Rocket(int playerID, int x, int y) {
        this.playerID = playerID;
        this.x = x + Player.SHIPWIDTH / 3 + 7;
        this.y = y + 15;
        rocketSize = 10;
        speed = 15;
        
    }
    
    // FUNCTION
    public boolean update() {
        
        y -= speed;
        
        if(x < 0 || y < 0 )
            return true;
        return false;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getSize() {
        return rocketSize;
    }
    
    public int getPlayerID() {
    	return playerID;
    }
}
