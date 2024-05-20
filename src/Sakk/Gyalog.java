package Sakk;

import java.util.LinkedList;
import java.util.List;

public class Gyalog extends Babu{
	private boolean lepett_mar;
	
	public Gyalog(char szin, Mezo kezdoMezo, String kep) {
		super(szin, kezdoMezo, kep);
	}
	
	public boolean lep(Mezo cel) {
		boolean b = super.lep(cel);
		lepett_mar = true;
		return b;
	}
	
	public List<Mezo> SzabalyosLepesek(Tabla t){
		LinkedList<Mezo> szabalyos_lepesek = new LinkedList<Mezo>();
		
		Mezo[][] palya = t.getPalya();
		
		int x = getMezo().getx();
		int y = getMezo().gety();
		char szin = getSzin();
		
		if(szin == 'b') {
			if(!lepett_mar) {
				if(!palya[y+2][x].vanBabu()) {
					szabalyos_lepesek.add(palya[y+2][x]);
				}
			}
			
			if(y+1 < palya.length) {
				if(!palya[y+1][x].vanBabu()) {
					szabalyos_lepesek.add(palya[y+1][x]);
				}
			}
			
			if (x+1< palya[0].length && y+1< palya.length && palya[y+1][x+1].vanBabu()) {
				szabalyos_lepesek.add(palya[y+1][x+1]);
			}
			
			if(x-1 > -1 && y+1 < palya.length && palya[y+1][x-1].vanBabu()) {
				szabalyos_lepesek.add(palya[y+1][x-1]);									
			}
		}
		
		else {
			if(!lepett_mar) {
				if(!palya[y-2][x].vanBabu()) {
					szabalyos_lepesek.add(palya[y-2][x]);
				}
			}
			if(y-1 > -1) {
				if(!palya[y-1][x].vanBabu()) {
					szabalyos_lepesek.add(palya[y-1][x]);
				}
			}
			
			if (x+1< palya[0].length && y-1 > -1 && palya[y-1][x+1].vanBabu()) {
				szabalyos_lepesek.add(palya[y-1][x+1]);
			}
			
			if(x-1 > -1 && y-1 > -1 && palya[y-1][x-1].vanBabu()) {
				szabalyos_lepesek.add(palya[y-1][x-1]);									
			}
			
		}
		return szabalyos_lepesek;
	}
}
