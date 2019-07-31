/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Balog
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import modell.Player;

public class GameMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage coin;

	Player player;
	Player player2;
	
    public GameMenu() {
        super();
        player = new Player(0);
        setBounds(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 4, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
        
        setBackground(Color.BLACK);
        
        try {
            coin = ImageIO.read(new File("face.png")); 
        }catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        int fontSize = 20;
        
        if (player != null) {        
	        for (int i = 0; i < player.getLives(); i++) {
	            g.drawImage(coin, 45 * i, 0, null);
	        }
	        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
	        g.setColor(Color.RED);
	        g.drawString("Score: " + Integer.toString(player.getScore()), 400, 20);
        }
        
        if (player2 != null) {
            for (int i = 0; i < player2.getLives(); i++) {
                g.drawImage(coin, GameFrame.width - 45 * i - 45, 0, null);
        	}        	
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
	        g.setColor(Color.RED);
	        g.drawString("Score: " + Integer.toString(player2.getScore()), 1000, 20);    
        }
    
    }
    
    public void draw(Player player, Player player2) {
        this.player = player;
        this.player2 = player2;
        repaint(); 
        
    }
    
}