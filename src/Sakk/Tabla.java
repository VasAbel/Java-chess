package Sakk;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JPanel;

public class Tabla extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ablak ablak;
	private boolean feherKor;
	private Babu babu;
	private Mezo[][] palya;
	LinkedList<Babu> FeherB, FeketeB;
	private List<Mezo> szabalyos_lepesek;
	private MattCheck mattkezelo;
	private int currx, curry;
	private static final String RESOURCES_FEHERLO_PNG = "feherlo.png";
	private static final String RESOURCES_FEKETELO_PNG = "feketelo.png";
	private static final String RESOURCES_FEHERGYALOG_PNG = "fehergyalog.png";
	private static final String RESOURCES_FEKETEGYALOG_PNG = "feketegyalog.png";
	private static final String RESOURCES_FEHERKIRALY_PNG = "feherkiraly.png";
	private static final String RESOURCES_FEKETEKIRALY_PNG = "feketekiraly.png";
	private static final String RESOURCES_FEHERVEZER_PNG = "fehervezer.png";
	private static final String RESOURCES_FEKETEVEZER_PNG = "feketevezer.png";
	private static final String RESOURCES_FEHERBASTYA_PNG = "feherbastya.png";
	private static final String RESOURCES_FEKETEBASTYA_PNG = "feketebastya.png";
	private static final String RESOURCES_FEKETEFUTO_PNG = "feketefuto.png";
	private static final String RESOURCES_FEHERFUTO_PNG = "feherfuto.png";
	private static final String RESOURCES_FEKETESHREK_PNG = "feketeshrek.png";
	private static final String RESOURCES_FEHERSHREK_PNG = "fehershrek.png";
	
	public Tabla(Ablak a, String fajl) {
		ablak = a;
		FeherB = new LinkedList<Babu>();
		FeketeB = new LinkedList<Babu>();
		
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		try {
			int[][] inic = beolvas(fajl);
			palya = new Mezo[inic.length][inic[0].length];
			setLayout(new GridLayout(palya.length,palya[0].length,0,0));
		for(int x = 0; x< palya.length; x++) {
			for(int y = 0; y < palya[0].length; y++) {
				if((x%2 == 0 && y%2 == 0) || (x%2 == 1 && y%2 == 1)) {  
					palya[x][y] = new Mezo(this, 'w', y, x);
					this.add(palya[x][y]);
				}
				else { 
					palya[x][y] = new Mezo(this, 'b', y, x);
					this.add(palya[x][y]); 
				}
				
			}
		}
		
		Kiraly fehk = null;
		Kiraly fekk = null;
		
		for(int i = 0; i < palya.length; i++) {
			for(int j = 0; j < palya[0].length; j++)
			switch(inic[i][j]) {
			case 1:
				palya[i][j].setBabu(new Bastya('b', palya[i][j], RESOURCES_FEKETEBASTYA_PNG));
				FeketeB.add(palya[i][j].getBabu());
				break;
			case 2: 
				palya[i][j].setBabu(new Lo('b', palya[i][j], RESOURCES_FEKETELO_PNG));
				FeketeB.add(palya[i][j].getBabu());
				break;
			case 3:
				palya[i][j].setBabu(new Futo('b', palya[i][j], RESOURCES_FEKETEFUTO_PNG));
				FeketeB.add(palya[i][j].getBabu());
				break;
			case 4:
				palya[i][j].setBabu(new Vezer('b', palya[i][j], RESOURCES_FEKETEVEZER_PNG));
				FeketeB.add(palya[i][j].getBabu());
				break;
			case 5:
				fekk = new Kiraly('b', palya[i][j], RESOURCES_FEKETEKIRALY_PNG);
				palya[i][j].setBabu(fekk);
				FeketeB.add(palya[i][j].getBabu());
				break;
			case 6:
				palya[i][j].setBabu(new Gyalog('b', palya[i][j], RESOURCES_FEKETEGYALOG_PNG));
				FeketeB.add(palya[i][j].getBabu());
				break;
			case -1:
				palya[i][j].setBabu(new Bastya('w', palya[i][j], RESOURCES_FEHERBASTYA_PNG));
				FeherB.add(palya[i][j].getBabu());
				break;
			case -2: 
				palya[i][j].setBabu(new Lo('w', palya[i][j], RESOURCES_FEHERLO_PNG));
				FeherB.add(palya[i][j].getBabu());
				break;
			case -3:
				palya[i][j].setBabu(new Futo('w', palya[i][j], RESOURCES_FEHERFUTO_PNG));
				FeherB.add(palya[i][j].getBabu());
				break;
			case -4:
				palya[i][j].setBabu(new Vezer('w', palya[i][j], RESOURCES_FEHERVEZER_PNG));
				FeherB.add(palya[i][j].getBabu());
				break;
			case -5:
				fehk = new Kiraly('w', palya[i][j], RESOURCES_FEHERKIRALY_PNG);
				palya[i][j].setBabu(fehk);
				FeherB.add(palya[i][j].getBabu());
				break;
			case -6:
				palya[i][j].setBabu(new Gyalog('w', palya[i][j], RESOURCES_FEHERGYALOG_PNG));
				FeherB.add(palya[i][j].getBabu());
				break;
			case 7:
				palya[i][j].setBabu(new Shrek('b', palya[i][j], RESOURCES_FEKETESHREK_PNG));
				FeketeB.add(palya[i][j].getBabu());
				break;
			case -7:
				palya[i][j].setBabu(new Shrek('w', palya[i][j], RESOURCES_FEHERSHREK_PNG));
				FeherB.add(palya[i][j].getBabu());
				break;
			default: break;
			}
		}
		 mattkezelo = new MattCheck(this, FeherB, FeketeB, fehk, fekk);
		
		} catch (FileNotFoundException e) {
			System.out.println("A szovegfajl nem talalhato");
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
		this.setSize(new Dimension(400, 400));
		
		feherKor = true;
		
	}
	
	
	
	public int[][] beolvas(String fajl) throws FileNotFoundException{
		Scanner sc = new Scanner(new BufferedReader(new FileReader(fajl)));
		int szelesseg = sc.nextInt();	
		int hossz = sc.nextInt();	
		sc.nextLine();
		int[][] tomb = new int[hossz][szelesseg];
		while(sc.hasNextLine()) {
			for(int i = 0; i < tomb.length; i++) {
				String[] sor = sc.nextLine().trim().split(" ");
				for(int j = 0; j < sor.length; j++)
					tomb[i][j] = Integer.parseInt(sor[j]);
			}
		}
		return tomb;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int x = 0; x < palya[0].length; x++) {
			for(int y = 0; y < palya.length; y++) {
				Mezo m = palya[y][x];
				m.paintComponent(g);
			}
		
		if(babu != null) {
			if((babu.getSzin() == 'w' && feherKor) || (babu.getSzin() == 'b' && !feherKor)) {
				Image pic = babu.getImage();
				g.drawImage(pic, currx, curry, null);
			}
		}
		}
	}
	
	public void Mentes() {
		int[][] fajlba = new int[palya.length + 2][palya[0].length];
		fajlba[0][0] = palya[0].length;
		fajlba[1][0] = palya.length;
		for(int i = 0; i < palya.length; i++)
			for(int j = 0; j < palya[0].length; j++) {
				if(palya[i][j].getBabu() == null)
					fajlba[i + 2][j] = 0;
				else if(palya[i][j].getBabu().getSzin() == 'b') {
					if(palya[i][j].getBabu().getClass() == Bastya.class) 
						fajlba[i + 2][j] = 1;
					else if(palya[i][j].getBabu().getClass() == Lo.class) 
						fajlba[i + 2][j] = 2;
					else if(palya[i][j].getBabu().getClass() == Futo.class) 
						fajlba[i + 2][j] = 3;
					else if(palya[i][j].getBabu().getClass() == Vezer.class) 
						fajlba[i + 2][j] = 4;
					else if(palya[i][j].getBabu().getClass() == Kiraly.class) 
						fajlba[i + 2][j] = 5;
					else if(palya[i][j].getBabu().getClass() == Gyalog.class) 
						fajlba[i + 2][j] = 6;
					else if(palya[i][j].getBabu().getClass() == Shrek.class) 
						fajlba[i + 2][j] = 7;
				}
				else {
					if(palya[i][j].getBabu().getClass() == Bastya.class) 
						fajlba[i + 2][j] = -1;
					else if(palya[i][j].getBabu().getClass() == Lo.class) 
						fajlba[i + 2][j] = -2;
					else if(palya[i][j].getBabu().getClass() == Futo.class) 
						fajlba[i + 2][j] = -3;
					else if(palya[i][j].getBabu().getClass() == Vezer.class) 
						fajlba[i + 2][j] = -4;
					else if(palya[i][j].getBabu().getClass() == Kiraly.class) 
						fajlba[i + 2][j] = -5;
					else if(palya[i][j].getBabu().getClass() == Gyalog.class) 
						fajlba[i + 2][j] = -6;
					else if(palya[i][j].getBabu().getClass() == Shrek.class) 
						fajlba[i + 2][j] = -7;
				}
			}
		
		
		try {
			PrintWriter iro = new PrintWriter("mentett.txt");
			iro.println(fajlba[0][0]);
			iro.println(fajlba[1][0]);
			for(int i = 2; i< fajlba.length; i++) {
				for(int j = 0; j <  fajlba[0].length; j++) {
					iro.print(fajlba[i][j]);
					if(j + 1 != fajlba[0].length)
						iro.print(" ");
				}
				iro.println();
			}
			iro.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		currx = e.getX() - 24;
		curry = e.getY() - 24;
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currx = e.getX();
		curry = e.getY();
		Mezo m = (Mezo) this.getComponentAt(new Point(e.getX(), e.getY()));
		
		if(m.vanBabu()) {
			babu = m.getBabu();
			if((babu.getSzin() == 'b' && feherKor) || babu.getSzin() == 'w' && !feherKor) return;
			m.setKirajzol(false);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Mezo m = (Mezo) getComponentAt(new Point(e.getX(), e.getY()));
		System.out.println(m.getx() +" "+ m.getY());
		if(babu != null) {
			if((babu.getSzin() == 'b' && feherKor) || (babu.getSzin() == 'w' && !feherKor) ) return;
			
			List<Mezo> babuLepesei = babu.SzabalyosLepesek(this);
			szabalyos_lepesek = mattkezelo.HovaLephet();
			System.out.println("hovalephet");
			if(szabalyos_lepesek.contains(m) && babuLepesei.contains(m) && mattkezelo.Ralephet(babu, m)) {
				System.out.println("ralephet");
				m.setKirajzol(true);
				babu.lep(m);
				
				mattkezelo.update();
				
				if(mattkezelo.fekMatt()) {
					babu = null;
					repaint();
					removeMouseListener(this);
					removeMouseMotionListener(this);
					ablak.Vege('w');
				}
				else if(mattkezelo.fehMatt()) {
					babu = null;
					repaint();
					removeMouseListener(this);
					removeMouseMotionListener(this);
					ablak.Vege('b');
				}
				else {
					babu = null;
					feherKor = !feherKor;
					szabalyos_lepesek = mattkezelo.HovaLephet();
					System.out.println("hovalephet a vegen");
				}
			}
			else { babu.getMezo().setKirajzol(true); babu = null;}
			
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public Mezo[][] getPalya(){
		return palya;
	}
	
	public MattCheck getMatt() {
		return mattkezelo;
	}
}
