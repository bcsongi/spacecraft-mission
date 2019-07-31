package controller;

import gui.GameFrame;
import gui.GameMenu;
import gui.GamePanel;
import gui.RankPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import modell.Enemy;
import modell.Player;
import modell.Rocket;

/**
 *
 * @author Balog
 */
public class MainController extends KeyAdapter implements Runnable, ActionListener {
    	
    private GameMenu menuPanel;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private RankPanel rankPanel;
    
    private Player player; 
    private Player player2;
    private ArrayList<Rocket> rockets;
    private ArrayList<Enemy> enemies;
    
    private Thread thread;
    boolean running = false;
        
    private int level;
    private boolean multyPlayerMode;
    
    public MainController(GameFrame gameFrame, GamePanel gamePanel, GameMenu menu, RankPanel rankPanel) {
        this.gamePanel = gamePanel;
        this.menuPanel = menu;
        this.gameFrame = gameFrame;
        this.rankPanel = rankPanel;
    }

    public void start(boolean multyPlayerMode) {
    	this.multyPlayerMode = multyPlayerMode;
        gameFrame.requestFocus();
        if(running == false) {
            thread = new Thread(this);
            running = true;
            thread.start();
        }
    }
    
    @Override
    public void run() {
    	level = 1;
    	
    	if (multyPlayerMode) {
    		player = new Player(1);
    		player2 = new Player(-1);
    	} else {
    		player = new Player(0);	
    	}
        
    	rockets = new ArrayList<Rocket>();
        enemies = new ArrayList<Enemy>();
        
        int FPS = 30;
        double averageFPS = 0;
        
        long startTime;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;
        
        int frameCount = 0;
        int maxFrameCount = 30;
        
        int targetTime = 1000 / FPS;
        
        while(running) {

            startTime = System.nanoTime();
            
            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - URDTimeMillis;
            
            try {
                Thread.sleep(waitTime);
            } catch(Exception e) { }
            
            totalTime += System.nanoTime() - startTime; 
            frameCount++;
            if(frameCount == maxFrameCount) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;                
            }
            
            // Player UPDATE
            if (player.update()) {
                rockets.add(new Rocket(1, player.getX(), player.getY()));
            }
            if (multyPlayerMode == true) {
            	if (player2.update()) {
                    rockets.add(new Rocket(2, player2.getX(), player2.getY()));
                }	
            }
            
            // Rocket UPDATE
            for(int i = 0; i < rockets.size(); i++) {
                boolean remove = rockets.get(i).update();
                if(remove) {
                    rockets.remove(i);
                    --i;
                }    
            }
            
            //Enemy UPDATE
            if(enemies.isEmpty()) {
                ++level;
                for(int i = 0; i < level * 2; i++)
                    enemies.add(new Enemy(level));
            }
            for(int i = 0; i < enemies.size(); i++) {
                boolean remove = enemies.get(i).update();
                if(remove) {
                    enemies.remove(i);
                    --i;
                }
            }
            
            // rocket enemy collosion
            for(int i = 0; i < rockets.size(); i++) {
                
                Rocket rocket = rockets.get(i);
                int rx = rocket.getX();
                int ry = rocket.getY();
                double rSize = rocket.getSize();
                    
                for(int j = 0; j < enemies.size(); j++) {
                
                    Enemy enemy = enemies.get(j);
                    double eSize = enemy.getSize();
                    double ex = enemy.getX() + eSize / 2;
                    double ey = enemy.getY();
                    
                    double rex = rx - ex;
                    double rey = ry - ey;
                     
                    double distance = Math.sqrt(rex * rex + rey * rey);
                    
                    if(distance < rSize + eSize / 2 - 7) {
                        if (rocket.getPlayerID() == 1) {
                        	player.setScore(player.getScore() + 1);
                        } else {
                        	player2.setScore(player2.getScore() + 1);
                        }
                    	enemies.remove(j); 
                        rockets.remove(i);
                        --i;
                        break;
                    }
                    
                }
            }
            
            // player enemy collosion
            if(!player.getRecovering()) {
                
                int px = player.getX();
                int py = player.getY();
                int pSize = Player.SHIPSIZE;
                for(int i = 0; i < enemies.size(); i++) {
                    
                    Enemy enemy = enemies.get(i);
                    int eSize = enemy.getSize();
                    int ex = enemy.getX() - eSize / 2;
                    int ey = enemy.getY() - 15;
                    
                    int dx = px - ex;
                    int dy = py - ey;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if(distance < pSize - eSize / 2 - 15) {
                        player.decreaseLife();
                        break;
                    }
               }            
            }
            
         // player2 enemy collosion
            if (multyPlayerMode) {
	            if(!player2.getRecovering()) {
	                
	                int px = player2.getX();
	                int py = player2.getY();
	                int pSize = Player.SHIPSIZE;
	                for(int i = 0; i < enemies.size(); i++) {
	                    
	                    Enemy enemy = enemies.get(i);
	                    int eSize = enemy.getSize();
	                    int ex = enemy.getX() - eSize / 2;
	                    int ey = enemy.getY() - 15;
	                    
	                    int dx = px - ex;
	                    int dy = py - ey;
	                    double distance = Math.sqrt(dx * dx + dy * dy);
	                    if(distance < pSize - eSize / 2 - 15) {
	                        player2.decreaseLife();
	                        break;
	                    }
	               }            
	            }
            }
            
            if (!multyPlayerMode) {
            	if (player.getLives() <= 0) {
                    running = false;
            	}
            } else {
            	if (player.getLives() <= 0 && player2.getLives() <= 0) {
                    running = false;
            	}
            }
            
            // DRAWING
            gamePanel.gameRender(averageFPS);
            gamePanel.draw(player, player2, rockets, enemies, running);
            menuPanel.draw(player, player2);
            
        }
        
        rankPanel.addToTheRanklist(player);
        if (multyPlayerMode) {
            rankPanel.addToTheRanklist(player2);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
   
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
            
        if (player.getLives() > 0) {
	        if (keyCode == KeyEvent.VK_LEFT) {
	            player.setLeft(true);
	        }
	        if (keyCode == KeyEvent.VK_RIGHT) {
	            player.setRigth(true);
	        }
	        if (keyCode == KeyEvent.VK_UP) {
	            player.setUp(true);
	        }    
	        if (keyCode == KeyEvent.VK_DOWN) {
	            player.setDown(true);
	        }        
	        if (keyCode == KeyEvent.VK_SPACE) {
	            player.setFiring(true);
	        }
        }
        
        if (multyPlayerMode) {
        	if (player2.getLives() > 0) {       
	        	if(keyCode == KeyEvent.VK_A) {
		            player2.setLeft(true);
		        }
		        if(keyCode == KeyEvent.VK_D) {
		            player2.setRigth(true);
		        }
		        if(keyCode == KeyEvent.VK_W) {
		            player2.setUp(true);
		        }    
		        if(keyCode == KeyEvent.VK_S) {
		            player2.setDown(true);
		        }        
		        if(keyCode == KeyEvent.VK_F) {
		            player2.setFiring(true);
		        }
        	}
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        
        if (player.getLives() > 0) {
	        if (keyCode == KeyEvent.VK_LEFT) {
	            player.setLeft(false);
	        }
	        if (keyCode == KeyEvent.VK_RIGHT) {
	            player.setRigth(false);
	        }
	        if (keyCode == KeyEvent.VK_UP) {
	            player.setUp(false);
	        }    
	        if (keyCode == KeyEvent.VK_DOWN) {
	            player.setDown(false);
	        }    
	        if (keyCode == KeyEvent.VK_SPACE) {
	            player.setFiring(false);
	        }
        }
        
        if (multyPlayerMode) {
            if (player2.getLives() > 0) { 
	        	if (keyCode == KeyEvent.VK_A) {
	                player2.setLeft(false);
	            }
	            if (keyCode == KeyEvent.VK_D) {
	                player2.setRigth(false);
	            }
	            if (keyCode == KeyEvent.VK_W) {
	                player2.setUp(false);
	            }    
	            if (keyCode == KeyEvent.VK_S) {
	                player2.setDown(false);
	            }    
	            if (keyCode == KeyEvent.VK_F) {
	                player2.setFiring(false);
	            }       	
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Start")) {
            start(false);
        } else if (e.getActionCommand().equalsIgnoreCase("StartMulty")) {
            start(true);
        }else if (e.getActionCommand().equalsIgnoreCase("Pause")) {
            System.out.println(Integer.MAX_VALUE + "pause");
            try {
                //Thread.wait(Long.MAX_VALUE);
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Exit")) { 
            System.exit(0);
        } 
    }
    
}
