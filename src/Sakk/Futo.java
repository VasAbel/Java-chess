package Sakk;

import java.util.List;

public class Futo extends Babu{

	public Futo(char szin, Mezo kezdoMezo, String k) {
		super(szin, kezdoMezo, k);
	}
	
	public List<Mezo> SzabalyosLepesek(Tabla t){
		Mezo[][] palya = t.getPalya();
		int x = this.getMezo().getx();
		int y = this.getMezo().gety();
		
		return SzabalyosAtlo(palya, x, y);
	}
}
