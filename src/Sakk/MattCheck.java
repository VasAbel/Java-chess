package Sakk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MattCheck {
	private Tabla t;
	private LinkedList<Babu> feherBabuk, feketeBabuk;
	private LinkedList<Mezo> lepesek; 
	private LinkedList<Mezo> mezok;
	private Kiraly feherk, feketek;
	private HashMap<Mezo, List<Babu>> feherlepes;
 	private HashMap<Mezo, List<Babu>> feketelepes;
	
	public MattCheck(Tabla t, LinkedList<Babu> fehb, LinkedList<Babu> fekb, Kiraly fehk, Kiraly fekk) {
		this.t = t;
		feherBabuk = fehb;
		feketeBabuk = fekb;
		feherk = fehk;
		lepesek = new LinkedList<Mezo>();
		mezok = new LinkedList<Mezo>();
		setFeketek(fekk);
		feherlepes = new HashMap<Mezo, List<Babu>>();
		setFeketelepes(new HashMap<Mezo, List<Babu>>());		//inicializálás
		Mezo[][] tabla = t.getPalya();
		
		for (int x = 0; x< t.getPalya()[0].length; x++)
			for(int y = 0; y < t.getPalya().length; y++) {
				mezok.add(tabla[y][x]);
				feherlepes.put(tabla[y][x], new LinkedList<Babu>());
				getFeketelepes().put(tabla[y][x], new LinkedList<Babu>());
			}
				
		update();											//játék állapotának frissítése
		
	}
	
	public void update() {
		Iterator<Babu> fehIter = feherBabuk.iterator();
		Iterator<Babu> fekIter = feketeBabuk.iterator();
		
		for(List<Babu> babuk: feherlepes.values())
			babuk.removeAll(babuk);
		
		for(List<Babu> babuk: getFeketelepes().values())
			babuk.removeAll(babuk);
		
		lepesek.removeAll(lepesek);
		
		while (fehIter.hasNext()) {
			Babu b = fehIter.next();
			
			if(!b.getClass().equals(Kiraly.class)){
				if(b.getMezo() == null) {
					fehIter.remove();
					continue;
				}
				List<Mezo> babuLepesei = b.SzabalyosLepesek(t);
				Iterator<Mezo> iter = babuLepesei.iterator();
				while(iter.hasNext()) {
					List<Babu> babuk = feherlepes.get(iter.next());
					babuk.add(b);					
				}
			}
				            
		}
		
		while (fekIter.hasNext()) {
			Babu b = fekIter.next();
			
			if(!b.getClass().equals(Kiraly.class)){
				if(b.getMezo() == null) {
					fekIter.remove();          
					continue;
				}
				List<Mezo> babuLepesei = b.SzabalyosLepesek(t);
				Iterator<Mezo> iter = babuLepesei.iterator();
				while(iter.hasNext()) {
					List<Babu> babuk = getFeketelepes().get(iter.next());
					babuk.add(b);					
				}
			}
				             
		}
	}
	
	public boolean fekSakk() {
		update();
		Mezo m = getFeketek().getMezo();
		if(feherlepes.get(m).isEmpty()) {
			lepesek.addAll(mezok);
			System.out.println("NINCS SAKK");	
			return false;
		}
		System.out.println("SAKK");
		return true;
	}
	
	public boolean fehSakk() {
		update();
		Mezo m = feherk.getMezo();
		if(getFeketelepes().get(m).isEmpty()) {
			lepesek.addAll(mezok);
			return false;
		}
		return true;
	}
	
	public boolean fekMatt() {
		boolean matt = true;
		if(!this.fekSakk())
			return false;
		System.out.println("fekmatt");
		 if(Blokkolhato(feherlepes.get(getFeketek().getMezo()), getFeketelepes(), getFeketek())) {
	    	System.out.println("blokkolhato");
	    	matt = false;}	
		 else if(Feloldhato(feherlepes, getFeketek())) {
				System.out.println("feloldhato");
				matt = false;}
			
		 else if(Utheto(getFeketelepes(), feherlepes.get(getFeketek().getMezo()), getFeketek())) {
		    	System.out.println("utheto");
		    	matt = false;}
			
			
		return matt;
	}
	
	public boolean fehMatt() {
		boolean matt = true;
		if(!this.fehSakk())
			return false;
			if(Blokkolhato(getFeketelepes().get(feherk.getMezo()), feherlepes, feherk))
				matt = false;
			if(Feloldhato(getFeketelepes(), feherk)) 
				matt = false;
			 if(Utheto(feherlepes, getFeketelepes().get(feherk.getMezo()), feherk))
				matt = false;
		
		return matt;
	}
	
	public boolean Feloldhato(Map<Mezo, List<Babu>> ellenfellepesek, Kiraly kir) {
		boolean kikerul = false;
		List<Mezo> kirLepesek = kir.SzabalyosLepesek(t);
		Iterator<Mezo> iter = kirLepesek.iterator();
		while(iter.hasNext()) {
			Mezo m = iter.next();
			if(!Ralephet(kir, m)) continue;
			if(ellenfellepesek.get(m).isEmpty()) {            //lepesek a mezőkhöz társítja a rálépni tudó bábukat. Ha az m-hez nem tartozik bábu, ideléphet a király
				this.lepesek.add(m);
				kikerul = true;
			}
		}
		return kikerul;
	}
	
	public boolean Utheto(Map<Mezo, List<Babu>> sajatlepesek, List<Babu> veszely, Kiraly kir){
		boolean utheto = false;
		if(veszely.size() == 1) {
			Mezo m = veszely.get(0).getMezo();
			
			if(kir.SzabalyosLepesek(t).contains(m)) {
				this.lepesek.add(m);
				if(Ralephet(kir, m))
					utheto = true;										
			}
			
			List<Babu> utok = sajatlepesek.get(m);
			ConcurrentLinkedDeque<Babu> para = new ConcurrentLinkedDeque<Babu>();
            para.addAll(utok);
			if(!para.isEmpty()) {
				this.lepesek.add(m);
				for(Babu b: para) {
					if(Ralephet(b, m))                         
						utheto = true;
				}
			}
			
		}
		return utheto;
			
	}
	
	public boolean Blokkolhato(List<Babu> veszely, Map<Mezo, List<Babu>> blokkolasok, Kiraly kir) {
		boolean blokkolhato = false;
		if(veszely.size() == 1) {
			Mezo tamadoMezo = veszely.get(0).getMezo();
			Mezo kiralyMezo = kir.getMezo();
			Mezo[][] tabla = t.getPalya();
			
			if(kiralyMezo.getx() == tamadoMezo.getx()) {
				for(int i = Math.min(kiralyMezo.gety(), tamadoMezo.gety()) + 1; i < Math.max(kiralyMezo.gety(), tamadoMezo.gety()); i++) {
					List<Babu> blokkoloBabu = blokkolasok.get(tabla[i][kiralyMezo.getx()]);   
					ConcurrentLinkedDeque<Babu> blokkolok = new ConcurrentLinkedDeque<Babu>();
					blokkolok.addAll(blokkoloBabu);    				//az erre a mezőre lépni tudó bábuk
					if(!blokkolok.isEmpty()) {
						this.lepesek.add(tabla[i][kiralyMezo.getx()]);
						for(Babu b: blokkolok)
							if(Ralephet(b, tabla[i][kiralyMezo.getx()])) 
								blokkolhato = true;
							
					}
				}
			}
			
			if(kiralyMezo.gety() == tamadoMezo.gety()) {
				for(int i = Math.min(kiralyMezo.getx(), tamadoMezo.getx()) + 1; i < Math.max(kiralyMezo.getx(), tamadoMezo.getx()); i++) {
					List<Babu> blokkoloBabu = blokkolasok.get(tabla[kiralyMezo.gety()][i]);
					ConcurrentLinkedDeque<Babu> blokkolok = new ConcurrentLinkedDeque<Babu>();
					blokkolok.addAll(blokkoloBabu);												//az erre a mezőre lépni tudó bábuk
					if(!blokkolok.isEmpty()) {
						this.lepesek.add(tabla[kiralyMezo.gety()][i]);
						for(Babu b: blokkolok)
							if(Ralephet(b, tabla[kiralyMezo.gety()][i])) 
								blokkolhato = true;
							
					}
				}
			}
			
			if(veszely.get(0).getClass().equals(Vezer.class) || veszely.get(0).getClass().equals(Futo.class)) {
				int kirX = kiralyMezo.getx();
				int kirY = kiralyMezo.gety();
				int tamX = tamadoMezo.getx();
				int tamY = tamadoMezo.gety();
				
				if(kirX > tamX && kirY > tamY) {
					for(int i = tamX + 1; i < kirX; i++) {
						tamY++;
						List<Babu> blokkoloBabu = blokkolasok.get(tabla[tamY][i]);
						ConcurrentLinkedDeque<Babu> blokkolok = new ConcurrentLinkedDeque<Babu>();
						blokkolok.addAll(blokkoloBabu);
						if(!blokkolok.isEmpty()) {
							this.lepesek.add(tabla[tamY][i]);
							for(Babu b: blokkolok)
								if(Ralephet(b, tabla[tamY][i])) 
									blokkolhato = true;
						}
					}
				
				}
				
				if(kirX < tamX && kirY < tamY) {
					for(int i = tamX - 1; i > kirX; i--) {
						tamY--;
						List<Babu> blokkoloBabu = blokkolasok.get(tabla[tamY][i]);
						ConcurrentLinkedDeque<Babu> blokkolok = new ConcurrentLinkedDeque<Babu>();
						blokkolok.addAll(blokkoloBabu);
						if(!blokkolok.isEmpty()) {
							this.lepesek.add(tabla[tamY][i]);
							for(Babu b: blokkolok)
								if(Ralephet(b, tabla[tamY][i])) 
									blokkolhato = true;
						}
					}
				
				}
				
				if(kirX > tamX && kirY < tamY) {
					for(int i = tamX + 1; i < kirX; i++) {
						tamY--;
						List<Babu> blokkoloBabu = blokkolasok.get(tabla[tamY][i]);
						ConcurrentLinkedDeque<Babu> blokkolok = new ConcurrentLinkedDeque<Babu>();
						blokkolok.addAll(blokkoloBabu);
						if(!blokkolok.isEmpty()) {
							this.lepesek.add(tabla[tamY][i]);
							for(Babu b: blokkolok)
								if(Ralephet(b, tabla[tamY][i])) 
									blokkolhato = true;
						}
					}
				
				}
				
				if(kirX < tamX && kirY > tamY) {
					for(int i = tamX - 1; i > kirX; i--) {
						tamY++;
						List<Babu> blokkoloBabu = blokkolasok.get(tabla[tamY][i]);
						ConcurrentLinkedDeque<Babu> blokkolok = new ConcurrentLinkedDeque<Babu>();
						blokkolok.addAll(blokkoloBabu);
						if(!blokkolok.isEmpty()) {
							this.lepesek.add(tabla[tamY][i]);
							for(Babu b: blokkolok)
								if(Ralephet(b, tabla[tamY][i])) 
									blokkolhato = true;
						}
					}
				
				}
			}
		}
		
		return blokkolhato;
	}	
	
	public List<Mezo> HovaLephet(){ 
		lepesek.removeAll(lepesek);
		if(fehSakk()) 	
			fehMatt();
		else if(fekSakk()) {
			System.out.println("helo");
			fekMatt();}
		return lepesek;
	}
	
	public boolean Ralephet(Babu b, Mezo m) {
		Babu aktBabu = m.getBabu();
		boolean lephet = true;
		Mezo honnanLep = b.getMezo();
		
		b.lep(m);
		update();
		
		if((b.getSzin() == 'w' && fehSakk()) || (b.getSzin() == 'b' && fekSakk()))
			lephet = false;
		
		b.lep(honnanLep);
		if(aktBabu != null) m.setBabu(aktBabu);
		update();
		
		lepesek.addAll(mezok);
		return lephet;
		
	}

	public HashMap<Mezo, List<Babu>> getFeketelepes() {
		return feketelepes;
	}

	public void setFeketelepes(HashMap<Mezo, List<Babu>> feketelepes) {
		this.feketelepes = feketelepes;
	}

	public Kiraly getFeketek() {
		return feketek;
	}

	public void setFeketek(Kiraly feketek) {
		this.feketek = feketek;
	}
}
	
