package Sakk;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Mezo extends JPanel{
 /**
	 * 
	 */

 private Tabla tabla;
 private char szin;
 private Babu babu;
 private int x, y;
 private boolean kirajzolhato;
 
 public Mezo(Tabla t, char s, int x, int y) {
	 tabla = t;
	 szin = s;
	 kirajzolhato = true;
	 this.x = x;
	 this.y = y;
	
	
 }
public boolean vanBabu() {
	return (this.babu != null);	
		
}
 public Babu getBabu() {
	 return babu;
 }
 
public int getx(){
	 return x;
 }
 
 public int gety() {
	 return y;
 }
 
 public void setBabu(Babu b) {
	 this.babu = b;
	 this.babu.setPos(this);
 }
 
 public void utes(Babu b) {
	 Babu akt = this.getBabu();
	 if(akt.getSzin() == 'b') 
		tabla.FeketeB.remove(akt);
	 if(akt.getSzin() == 'w') tabla.FeherB.remove(akt);
	 babu = b;
 }
 

 public void paintComponent(Graphics g) {
	
	 super.paintComponent(g);
	 
	 if(szin == 'b')
		 g.setColor(new Color(117, 109, 114));
	 else g.setColor(new Color(192, 192, 192));
	 
	 g.fillRect(0, 0, this.getWidth(), this.getHeight());
	 if(babu != null && kirajzolhato)
		 babu.kirajzol(g);
	// System.out.println(super.getX() + " " + super.getY() + " " + getWidth() + " " + getHeight());
 }
 
 public Babu torles() {
	 Babu b = babu;
	 babu = null;
	 return b;
 }
 
 public void setKirajzol(boolean b) {
	 kirajzolhato = b;
 }

 
}
