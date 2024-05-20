package Sakk;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Ablak {
private Tabla tabla;
private JFrame ablak;

	public Ablak(String fajl) {
		ablak = new JFrame("Sakk");
		
		try {
			Image kep = ImageIO.read(getClass().getResource("index.png"));
			ablak.setIconImage(kep);
		}catch(Exception e) {System.out.println("HOL A KEP");}
		ablak.setLocation(100, 100);
		ablak.setLayout(new BorderLayout(20, 20));
		this.tabla = new Tabla(this, fajl);
		
		ablak.add(tabla, BorderLayout.CENTER);
		ablak.add(gombok(), BorderLayout.NORTH);
		ablak.setMinimumSize(ablak.getPreferredSize());
		ablak.setSize(ablak.getPreferredSize());	
		ablak.setResizable(true);
		ablak.pack();
		ablak.setVisible(true);
		ablak.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JPanel gombok() {
		JPanel gombok = new JPanel();
		gombok.setLayout(new GridLayout());
		
		JButton kilep = new JButton("Kilepes");
		kilep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int n = JOptionPane.showConfirmDialog(ablak, "Szeretned menteni a jatekot?", "Mentes", JOptionPane.YES_NO_OPTION);
				
			if (n==JOptionPane.YES_OPTION) {
				tabla.Mentes();
				ablak.dispose();                                                                                                      //Mentést meg kell valósítani
			}
			
			else ablak.dispose();
			}
		});
		
		JButton ff = new JButton("Feladas");
		ff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			SwingUtilities.invokeLater(new Menu());
			ablak.dispose();
			}
		});
		
		gombok.add(kilep);
		gombok.add(ff);
		gombok.setPreferredSize(gombok.getMinimumSize());
		return gombok;
	}
	
	public void Vege(char gyoztes) {
		if(gyoztes == 'w') {
			int i = JOptionPane.showConfirmDialog(ablak, "Feher nyert.");
			if(i == JOptionPane.OK_OPTION) {
				SwingUtilities.invokeLater(new Menu());
				ablak.dispose();
			}
		}
		else { 
			int j = JOptionPane.showConfirmDialog(ablak, "Fekete nyert.");
			if(j == JOptionPane.OK_OPTION) {
				SwingUtilities.invokeLater(new Menu());
				ablak.dispose();
			}
		}
	}
	
	public Tabla getTabla() {
		return tabla;
	}
}
