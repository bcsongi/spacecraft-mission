package gui;

/**
 *
 * @author Balog
 */
import controller.MainController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Balog
 */
public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// FIELDS
    public static int width;
    public static int height;
    
    private GamePanel gamePanel;
    private GameMenu gameMenu;
    private RankPanel rankPanel;
    
    private MainController mainController;
    
    private JButton startButton;
    private JButton startMultyButton;
    private JButton pauseButton;
    private JButton exitButton;

    private MyContentPane contentPane;
    
    private Dimension screenSize;
 
    // CONSTRUCTOR 
    public GameFrame(String title, int width, int height) {
        super(title);
        
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GameFrame.width = (int)screenSize.getWidth();
        GameFrame.height = (int)screenSize.getHeight();;

        setLayout(new BorderLayout());
        setBounds(0, 0, GameFrame.width, GameFrame.height);
        
        gamePanel = new GamePanel(); 
        gameMenu = new GameMenu();
        rankPanel = new RankPanel();
         	
        contentPane = new MyContentPane();
        contentPane.setLayout(new BorderLayout());
        
        mainController = new MainController(this, gamePanel, gameMenu, rankPanel);
        
        startButton = new JButton("Start");
        startButton.setBounds(width / 4 - 40, height / 4 - 90, 70, 70);
        gameMenu.add(startButton);

        startMultyButton = new JButton("StartMulty");
        startMultyButton.setBounds(width / 4 - 70, height / 4 - 90, 70, 70);
        gameMenu.add(startMultyButton);
        
        pauseButton = new JButton("Pause");
        pauseButton.setBounds(width / 4, height / 4 - 90, 70, 70);
        gameMenu.add(pauseButton);
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(width / 4 + 90, height / 4 - 90, 70, 70);
        gameMenu.add(exitButton);
        
        startButton.addActionListener(mainController);
        startMultyButton.addActionListener(mainController);
        pauseButton.addActionListener(mainController);
        exitButton.addActionListener(mainController);
        
        contentPane.add(gamePanel, BorderLayout.CENTER);
        contentPane.add(gameMenu, BorderLayout.NORTH);
        contentPane.add(rankPanel, BorderLayout.EAST);
        
        this.setResizable(false);
        //this.setUndecorated(true);
        this.setContentPane(contentPane);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(mainController);   
    }
}