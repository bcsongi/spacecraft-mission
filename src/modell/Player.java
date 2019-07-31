package modell;

import gui.GameFrame;

/**
 *
 * @author Balog
 */
public class Player {

    // FIELDS
    public static final int SHIPWIDTH = 100;
    public static final int SHIPHEIGHT = 116;
    public static final int SHIPSIZE = 116;
    
    private int x;
    private int y;
    private int dx;    
    private int dy;    
    private int speed;
    private int score;
    
    private boolean left;
    private boolean right;
    private boolean up; 
    private boolean down;
    
    private int lives;
    
    private boolean firing;
    private long firingTimer;
    private long firingDelay;
    
    private boolean recovering;
    private double recoverTime;
    
    // CONSTRUCTOR
    public Player(int pos) {
    	if (pos == 0) {
    		x = GameFrame.width / 2;
    	} else if (pos == 1) {
    		x = GameFrame.width / 2 + GameFrame.width / 4 ;	
    	} else if (pos == -1){
    		x = GameFrame.width / 2 - GameFrame.width / 4 ;
    	} else {
    		x = GameFrame.width * 2;
    	}
    	
    	y = GameFrame.height / 2;
     
        dx = 0;
        dy = 0;
        speed = 5;
        this.score = 0;
        
        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 200;
        lives = 3;
        
        recovering = false;
        recoverTime = 0;
        
    }
    
    public boolean update() {
        
        if(left) {
            dx = -speed;
        } 
        if(right) {
            dx = speed;
        }
        
        x += dx;
        y += dy;
        
        if(up) {
            dy = -speed;
        }
        if(down) {
            dy = speed; 
        }

        x += dx;
        y += dy;
        
        if(x < 0) {
            x = 0;
        }
        if(y < 0) {
            y = 0;
        }
        if(x > GameFrame.width - Player.SHIPWIDTH) {
            x = GameFrame.width - Player.SHIPWIDTH;
        }
        if(y > GameFrame.height - Player.SHIPHEIGHT) {
            y = GameFrame.height - Player.SHIPHEIGHT;
        }
        
        dx = 0;
        dy = 0;
        long elapsed = (long) (System.nanoTime() - recoverTime) / 1000000;
        if (elapsed > 2000) {
            recovering = false;
            recoverTime = 0;
        }
        
        if (firing) {
            elapsed = (System.nanoTime() - firingTimer) / 1000000; 
            if(elapsed > firingDelay) {
                firingTimer = System.nanoTime();
                return true;
            }
        }

        return false;
    }
    
    public void decreaseLife() {
        --lives;
        recovering = true;
        recoverTime = System.nanoTime();
    }
    
    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public int getX() {
        return x;
    }
    
    public void setX(int newX) {
        x = newX;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int newY) {
        x = newY;
    }
    
    public int getDX() {
        return dx;
    }
    
    public void setDX(int newDX) {
        dx = newDX;
    }
    
    public int getDY() {
        return dy;
    }
    
    public void setDY(int newDY) {
        dy = newDY;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setScore(int score) {
    	this.score = score;
    }
    
    public int getScore() {
    	return score;
    }
    
    public boolean getLeft() {
        return left;
    }
    
    public void setLeft(boolean newLeft) {
        left = newLeft; 
    }
    
    public boolean getRight() {
        return right;
    }
    
    public void setRigth(boolean newRight) {
        right = newRight; 
    }
    
    public boolean getUp() {
        return up;
    }
    
    public void setUp(boolean newUp) {
        up = newUp; 
    }
    
    public boolean getDown() {
        return down;
    }
    
    public void setDown(boolean newDown) {
        down = newDown; 
    }
    
    public int getLives() {
        return lives;
    }
    
    public boolean getRecovering() {
        return recovering;
    }
}