package Sakk;

import java.util.LinkedList;
import java.util.List;

public class Kiraly extends Babu {
 public Kiraly(char szin, Mezo kezdoMezo, String k) {
	 super(szin, kezdoMezo, k);
 }
 
 public List<Mezo> SzabalyosLepesek(Tabla t){
	 LinkedList<Mezo> szabalyos_lepesek = new LinkedList<Mezo>();
	 Mezo[][] palya = t.getPalya();
	 
	 int x = getMezo().getx();
	 int y = getMezo().gety();
	 
	 for(int i = -1; i <= 1; i++) {
		 for(int j = -1; j <= 1; j++) {
			 if(!(i==0 && j==0)) {
				 try {
					 if(!palya[y+j][x+i].vanBabu() || palya[y+j][x+i].getBabu().getSzin() != getSzin()) {
						 szabalyos_lepesek.add(palya[y+j][x+i]);
					 }
				 } catch (ArrayIndexOutOfBoundsException e) {
					 continue;
				 }
			 }
		 }
	 }
	 return szabalyos_lepesek;
 }
}
