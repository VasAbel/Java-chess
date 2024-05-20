package Sakk;

import java.util.LinkedList;
import java.util.List;

public class Vezer extends Babu{

	public Vezer(char szin, Mezo kezdoMezo, String k) {
		super(szin, kezdoMezo, k);
	}
	
	public List<Mezo> SzabalyosLepesek(Tabla t){
		LinkedList<Mezo> szabalyos_lepesek = new LinkedList<Mezo>();
		Mezo[][] palya = t.getPalya();
		
		int x = getMezo().getx();
		int y = getMezo().gety();
		
		int[] szabalyosHossz = SzabalyosHossz(palya, x, y);
		
		for(int i = szabalyosHossz[0]; i <= szabalyosHossz[1]; i++)
			if(i != y) szabalyos_lepesek.add(palya[i][x]);
		for(int i = szabalyosHossz[2]; i <= szabalyosHossz[3]; i++)
			if (i != x) szabalyos_lepesek.add(palya[y][i]);
		
		List<Mezo> szabalyosAtlo = SzabalyosAtlo(palya, x, y);
		
		szabalyos_lepesek.addAll(szabalyosAtlo);
		
		return szabalyos_lepesek;
	}
}
