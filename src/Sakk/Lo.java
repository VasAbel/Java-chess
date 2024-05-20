package Sakk;

import java.util.LinkedList;
import java.util.List;

public class Lo extends Babu {
	
	public Lo(char szin, Mezo kezdoMezo, String kep) {
		super(szin, kezdoMezo, kep);
	}
	
	public List<Mezo> SzabalyosLepesek(Tabla t){
		LinkedList<Mezo> szabalyos_lepesek = new LinkedList<Mezo>();
		
		Mezo[][] palya = t.getPalya();
		
		int x = getMezo().getx();
		int y = getMezo().gety();
		
		for(int i = 2; i >= -2; i--) {
			for(int k = 2; k >= -2; k--) {
				if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
					if(k != 0 && i != 0) {
						try {
							szabalyos_lepesek.add(palya[y+k][x+i]);
						}catch(ArrayIndexOutOfBoundsException e) {
							continue;
						}
					}
				}
			}
		}
		return szabalyos_lepesek;
	}
	
}
