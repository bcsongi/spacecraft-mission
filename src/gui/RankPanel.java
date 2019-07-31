package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modell.Player;

public class RankPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel label;
	private JTextField name;
	private JButton addButton;
	private Player player;

	private ArrayList<JLabel> topRanks;
	
	public RankPanel() {
		
		label = new JLabel("RankList:");
		label.setSize(15, 15);
	//	label.setBackground(Color.red);
		
		name = new JTextField(15);
		addButton = new JButton("ADD");
		topRanks = new ArrayList<JLabel>();
		
		add(name);
		add(addButton);
		add(label);
		
		name.setBounds(0, 0 , 40, 50);
		addButton.setBounds(0, 50 , 40, 50);
		
		addButton.addActionListener(this);
		
		setLayout(new GridLayout(22, 2));
		setBounds(GameFrame.width / 4, GameFrame.width / 4, 400, 400);
		
		setBackground(Color.blue);
		setFocusable(true);
		setVisible(false);
	}
	
	public void addToTheRanklist(Player player) {
		requestFocus();
		setVisible(true);
		
		this.player = player;
		
		BufferedReader br;
		StringTokenizer st;
        
		try {
			br = new BufferedReader(new FileReader("ranklist.txt"));
			while ((st = new StringTokenizer(br.readLine())) != null) {
				topRanks.add(new JLabel(st.nextToken() + "   " + st.nextToken()));	
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e) { }
		
		for (JLabel rank : topRanks) {
			add(rank);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("ranklist.txt", true)));
			out.println(name.getText() + " " + player.getScore());
			// writer = new PrintWriter("ranklist.txt", "UTF-8");
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (JLabel rank : topRanks) {
			remove(rank);
		}		
		topRanks.clear();
		setVisible(false);	
	}
	
}
