package Sakk;

import java.util.LinkedList;
import java.util.List;

public class Shrek extends Babu {
	public Shrek(char szin, Mezo kezdoMezo, String k) {
		super(szin, kezdoMezo, k);
	}
	
	public List<Mezo> SzabalyosLepesek(Tabla t){
LinkedList<Mezo> szabalyos_lepesek = new LinkedList<Mezo>();
		
		Mezo[][] palya = t.getPalya();
		
		int x = getMezo().getx();
		int y = getMezo().gety();
		char szin = getSzin();
		
		if(szin == 'b') {					
			if(y+1 < palya.length) {
				if(!palya[y+1][x].vanBabu()) {
					szabalyos_lepesek.add(palya[y+1][x]);
				}
			}
			
			if (x+1< palya[0].length && y+1< palya.length && palya[y+1][x+1].vanBabu()) {
				szabalyos_lepesek.add(palya[y+1][x+1]);
				if (x+2< palya[0].length && y+2< palya.length && palya[y+2][x+2].vanBabu())
					szabalyos_lepesek.add(palya[y+2][x+2]);
			}
			
			if(x-1 > -1 && y+1 < palya.length && palya[y+1][x-1].vanBabu()) {
				szabalyos_lepesek.add(palya[y+1][x-1]);	
				if(x-2 > -1 && y+2 < palya.length && palya[y+2][x-2].vanBabu()) {
					szabalyos_lepesek.add(palya[y+2][x-2]);	
			}
		}
		}
		else {
			if(y-1 > -1) {
				if(!palya[y-1][x].vanBabu()) {
					szabalyos_lepesek.add(palya[y-1][x]);
				}
			}
			
			if (x+1< palya[0].length && y-1 > -1 && palya[y-1][x+1].vanBabu()) {
				szabalyos_lepesek.add(palya[y-1][x+1]);
				if (x+2< palya[0].length && y-2 > -1 && palya[y-2][x+2].vanBabu())
					szabalyos_lepesek.add(palya[y-2][x+2]);
			}
			
			if(x-1 > -1 && y-1 > -1 && palya[y-1][x-1].vanBabu()) {
				szabalyos_lepesek.add(palya[y-1][x-1]);		
				if(x-2 > -1 && y-2 > -1 && palya[y-2][x-2].vanBabu())
					szabalyos_lepesek.add(palya[y-2][x-2]);
			}
		}
		
		
		
		return szabalyos_lepesek;
	
	
	}
}
