package Sakk;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Babu {

	private char szin;
	private Mezo mezo;
	private BufferedImage kep;
	
	public Babu(char sz, Mezo m, String k) {
		szin = sz;
		mezo = m;
		
		try {
			if(this.kep == null) {
				this.kep = ImageIO.read(getClass().getResource(k));
			}
		}
		catch (IOException io) {
			System.out.println("Kephiba");
		}
	}
	
	public Image getImage() {
		return kep;
	}
	
	public boolean lep(Mezo cel) {
		Babu jelenlegi = cel.getBabu();
		
		if(jelenlegi != null) {
			if(jelenlegi.getSzin()== this.szin) return false;
			else cel.utes(this);
		}
		
		mezo.torles();
		mezo = cel;
		mezo.setBabu(this);
		return true;
	}
	
	public char getSzin() {
		return szin;
	}
	
	public Mezo getMezo() {
		return mezo;
	}
	
	public void kirajzol(Graphics g) {
		g.drawImage(kep, 0, 0, null);
	}
	
	public void setPos(Mezo m) {
		mezo = m;
	}
	
	public int[] SzabalyosHossz(Mezo[][] palya, int x, int y) {
		int felfele = 0;
		int lefele = palya.length - 1;
		int jobbra = palya[0].length - 1;
		int balra = 0;
		
		for(int i = 0; i<y; i++) {
			if(palya[i][x].vanBabu()) { 
				if(palya[i][x].getBabu().getSzin() == szin)
					felfele = i + 1;	
				else felfele = i;
			}
		}
		
		for(int i = 0; i<x; i++) {
			if(palya[y][i].vanBabu()) { 
				if(palya[y][i].getBabu().getSzin() == szin)
					balra = i + 1;	
				else balra = i;
			}
		}
		
		for(int i = palya.length - 1; i>y; i--) {
			if(palya[i][x].vanBabu()) { 
				if(palya[i][x].getBabu().getSzin() == szin)
					lefele = i - 1;	
				else lefele = i;
			}
		}
		
		for(int i = palya[0].length - 1; i > x; i--) {
			if(palya[y][i].vanBabu()) { 
				if(palya[y][i].getBabu().getSzin() == szin)
					jobbra = i - 1;	
				else jobbra = i;
			}
		}
		
		int[] szabalyosHossz = {felfele, lefele, balra, jobbra};
		return szabalyosHossz;
	}
	
	public List<Mezo> SzabalyosAtlo(Mezo[][] palya, int x, int y) {
		LinkedList<Mezo> szabalyosAtlo = new LinkedList<Mezo>();
		
		int balfelX = x-1;
		int balfelY = y-1;
		int jobbfelX = x+1;
		int jobbfelY = y-1;
		int balleX = x-1;
		int balleY = y+1;
		int jobbleX = x+1;
		int jobbleY = y+1;
		
		while(balfelX >= 0 && balfelY >= 0) {
			if(palya[balfelY][balfelX].vanBabu()) {
				if(palya[balfelY][balfelX].getBabu().getSzin() == this.szin)
					break;
				else {szabalyosAtlo.add(palya[balfelY][balfelX]); break;}					
			}
			else {
				szabalyosAtlo.add(palya[balfelY][balfelX]);		
				balfelY--;
				balfelX--;
			}
		}
		
		while(jobbfelX < palya[0].length && jobbfelY >= 0) {
			if(palya[jobbfelY][jobbfelX].vanBabu()) {
				if(palya[jobbfelY][jobbfelX].getBabu().getSzin() == szin)
					break;
				else {szabalyosAtlo.add(palya[jobbfelY][jobbfelX]); break;}					
			}
			else {
				szabalyosAtlo.add(palya[jobbfelY][jobbfelX]);		
				jobbfelY--;
				jobbfelX++;
			}
		}
		
		while(jobbleX < palya[0].length && jobbleY < palya.length) {
			if(palya[jobbleY][jobbleX].vanBabu()) {
				if(palya[jobbleY][jobbleX].getBabu().getSzin() == szin)
					break;
				else {szabalyosAtlo.add(palya[jobbleY][jobbleX]); break;}					
			}
			else {
				szabalyosAtlo.add(palya[jobbleY][jobbleX]);		
				jobbleY++;
				jobbleX++;
			}
		}
		
		while(balleX >= 0 && balleY < palya.length) {
			if(palya[balleY][balleX].vanBabu()) {
				if(palya[balleY][balleX].getBabu().getSzin() == szin)
					break;
				else {szabalyosAtlo.add(palya[balleY][balleX]); break;}					
			}
			else {
				szabalyosAtlo.add(palya[balleY][balleX]);	
				balleY++;
				balleX--;
			}
		}
		return szabalyosAtlo;
	}
	
	
	public abstract List<Mezo> SzabalyosLepesek(Tabla t);
}
