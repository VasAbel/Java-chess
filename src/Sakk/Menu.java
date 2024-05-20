package Sakk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu implements Runnable {
	public void run() {
		JFrame start = new JFrame("Sakk");
		start.setLayout(new BorderLayout(30, 30));
		start.setSize(new Dimension(400, 400));
		JPanel gombok = new JPanel();
		gombok.setLayout(new FlowLayout());
		
		JButton kilep = new JButton("Kilepes");
		kilep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {	
				start.dispose();                                                                                                      //Mentést meg kell valósítani
			}
		});
			
		JButton jatek = new JButton("Jatek");
		jatek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new Ablak("palya.txt");
				start.dispose();
			}
		});
		
		JButton betoltes = new JButton("Korabbi jatek betoltese");
		betoltes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new Ablak("mentett.txt");
				start.dispose();
			}
		});
		
		gombok.add(jatek);
		gombok.add(kilep);
		gombok.add(betoltes);
		start.add(gombok, BorderLayout.CENTER);
		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.setVisible(true);
		
		
	}
	
}
