package framework.view;

import javax.swing.*;

import framework.model.GameModel;

import java.awt.*;
import java.awt.event.*;

public abstract class GameLaunchPage extends JFrame {
    JButton startButton;
    JButton loadButton;
    JButton quitButton;
    
    public abstract void newGame();
    public abstract void loadGame();
    public abstract void startGame(GameModel model);

    public GameLaunchPage() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();				
			}
		});
        
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();				
			}
		});
        
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});

        JPanel buttons = new JPanel();

        buttonPanel.add(startButton, BorderLayout.NORTH);
        buttonPanel.add(loadButton, BorderLayout.CENTER);
        buttonPanel.add(quitButton, BorderLayout.SOUTH);
        
        buttons.add(buttonPanel);
        this.add(buttons, BorderLayout.CENTER);
        
        setSize(200, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

}