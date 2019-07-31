package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import modell.Enemy;
import modell.Player;
import modell.Rocket;

/**
 *
 * @author Balog
 */
public class GamePanel extends JPanel {
  
	private static final long serialVersionUID = 1L;
	
	private BufferedImage backgroundImage;
    private BufferedImage spaceShip;
    private BufferedImage spaceShip2;
    private BufferedImage rspaceShip;
    private BufferedImage enemyShip;
    private BufferedImage gameOver;
    private BufferedImage fps;
    private Graphics2D g;
    
    private Player player;
    private Player player2;
    private ArrayList<Rocket> rockets;
    private ArrayList<Enemy> enemies;
    
    private boolean running;
    
    public GamePanel() {
        
        super();        
        setBounds(0, 0, GameFrame.width, GameFrame.height);
        setBackground(Color.red);
        setFocusable(true);
        
        player = new Player(99);
        rockets = new ArrayList<>();
        enemies = new ArrayList<>();
        running = true;
        
        fps = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)fps.getGraphics();
        
        try {
            backgroundImage = ImageIO.read(new File("background.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            spaceShip = ImageIO.read(new File("spaceship.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            spaceShip2 = ImageIO.read(new File("spaceship2.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            rspaceShip = ImageIO.read(new File("rspaceship.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            enemyShip = ImageIO.read(new File("enemyship2.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            gameOver = ImageIO.read(new File("gameover.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(Graphics g) {
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        g.drawImage(backgroundImage, 0, 0, this);
        
        if (player.getLives() > 0) {
	        if (player.getRecovering()) {
	            g.drawImage(rspaceShip, player.getX() , player.getY(), null);           
	        } else {
	            g.drawImage(spaceShip, player.getX() , player.getY(), null);
	        }
        }
        
        if (player2 != null && player2.getLives() > 0) {
        	if (player2.getRecovering()) {
                g.drawImage(rspaceShip, player2.getX() , player2.getY(), null);           
            } else {
                g.drawImage(spaceShip2, player2.getX() , player2.getY(), null);
            }	
        }
        
        for(Rocket current : rockets) {
            g.setColor(Color.WHITE);
            g.drawOval(current.getX(), current.getY(), current.getSize(), current.getSize());
        }
        
        for(Enemy current : enemies) {
            g.drawImage(enemyShip, current.getX() , current.getY(), null);
        }
        
        if(running == false)
            g.drawImage(gameOver, GameFrame.width / 4, GameFrame.height / 4, null);
           
    }
    
    public void gameUpdate() {
    
    }

    public void gameRender(double FPS) {
        //draw background
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, GameFrame.width, GameFrame.height);
        //draw FPS
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + FPS, 100, 100);
    }

    public void draw(Player player, Player player2, ArrayList<Rocket> rockets,ArrayList<Enemy> enemies, boolean running) {        
        this.player = player;
        this.player2 = player2;
        this.rockets = new ArrayList<>(rockets);
        this.enemies = new ArrayList<>(enemies);
        this.running = running;
        repaint();
    }
    
    public void gameDraw() {
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        g2.drawImage(fps, 0, 0, null);
        g2.dispose();
    }
   
}
