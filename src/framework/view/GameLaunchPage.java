package framework.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class GameLaunchPage extends JFrame {
    JButton startButton;
    JButton loadButton;
    JButton quitButton;

    JPanel textpanel;
    
    public abstract void startGame();
    public abstract void loadGame();
    
    public GameLaunchPage() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();				
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

        textpanel = new JPanel();
        JTextArea info = new JTextArea("Enter difficulty (3 - 5)");
        info.setEditable(false);
        JTextField diff = new JTextField("3");
        
        textpanel.add(info, BorderLayout.CENTER);
        textpanel.add(diff, BorderLayout.SOUTH);

        add(textpanel, BorderLayout.SOUTH);
        
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

}