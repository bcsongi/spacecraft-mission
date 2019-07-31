/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

import gui.GameFrame;

import java.util.Random;

public class Enemy {

    private int x;
    private int y;
    private int size;
            
    private double angle;
    private double dx;
    private double dy;
    
    private int speed;
    private int type;
    private boolean died;
    
    private Random rand;
   
    public Enemy(int type) {
        
        rand = new Random();
        this.type = type;
        this.died = false;

        this.speed = 2 * type;
        this.size = 59;

        this.x = rand.nextInt(GameFrame.width - size / 2);
        this.y = 0;
               
        setAngle();
    }
   
    private void setAngle() {

        angle = Math.toRadians(rand.nextDouble() * 140 + 20);
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
    
    }
    
    public boolean update() {
        
        x += dx;
        y += dy;
        
        if(x < -9 && dx < 0)
            dx = -dx;
        if(x > GameFrame.width - size && dx > 0) 
            dx = -dx;
        
        if(y > GameFrame.height)
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
        return size;
    }
    
    public int getType() {
        return type;
    }
    
    public boolean getDied() {
        return died;
    }
    
}
