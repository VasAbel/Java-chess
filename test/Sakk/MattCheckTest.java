package Sakk;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MattCheckTest {

	Ablak a;
	
	@Before
	public void setUp() {
		 a = new Ablak("teszt.txt");
		 
	}
	
	@Test
	public void testBuild() {
		Mezo m = (Mezo) a.getTabla().getComponentAt(new Point(250, 3));
		
		assertTrue(m.getBabu().getClass() == Futo.class);
	}
	
	@Test
	public void testLep() {
		Mezo m = a.getTabla().getPalya()[7][3];
		Babu v = m.getBabu();
		v.lep(a.getTabla().getPalya()[5][5]);
		a.getTabla().getMatt().update();
		assertNotNull(a.getTabla().getPalya()[5][5].getBabu());
	}
	
	@Test
	public void testRalephet() {
		Mezo m = a.getTabla().getPalya()[5][3];
		boolean l = a.getTabla().getMatt().Ralephet(m.getBabu(), a.getTabla().getPalya()[1][7]);
		assertTrue(l);
	}
		
	
	@Test
	public void testBlokkolhato(){
		Mezo m = a.getTabla().getPalya()[5][3];
		Babu f = m.getBabu();
		f.lep(a.getTabla().getPalya()[4][1]);
		a.getTabla().getMatt().update();
		List<Babu> veszely = new LinkedList<Babu>();
		veszely.add(f);
		Map<Mezo, List<Babu>> map = a.getTabla().getMatt().getFeketelepes();
		Kiraly kiraly = a.getTabla().getMatt().getFeketek();
		boolean b = a.getTabla().getMatt().Blokkolhato(veszely, map, kiraly);
		assertTrue(b);
	}
	
	@Test
	public void testUtheto() {
		Mezo m = a.getTabla().getPalya()[5][3];
		Babu f = m.getBabu();
		f.lep(a.getTabla().getPalya()[4][1]);
		a.getTabla().getMatt().update();
		List<Babu> veszely = new LinkedList<Babu>();
		veszely.add(f);
		Map<Mezo, List<Babu>> map = a.getTabla().getMatt().getFeketelepes();
		Kiraly kiraly = a.getTabla().getMatt().getFeketek();
		boolean b = a.getTabla().getMatt().Utheto(map, veszely, kiraly);
		assertFalse(b);
	}
	
	@Test
	public void testfekMatt() {
		Mezo m = a.getTabla().getPalya()[5][3];
		Babu f = m.getBabu();
		Mezo m1 = a.getTabla().getPalya()[1][5];
		Babu g = m1.getBabu();
		Mezo m2 = a.getTabla().getPalya()[1][6];
		Babu g2 = m2.getBabu();
		Mezo m3 = a.getTabla().getPalya()[3][3];
		Babu g3 = m3.getBabu();
		g.lep(a.getTabla().getPalya()[2][5]);
		a.getTabla().getMatt().update();
		g2.lep(a.getTabla().getPalya()[3][6]);
		a.getTabla().getMatt().update();
		g3.lep(a.getTabla().getPalya()[1][3]);
		a.getTabla().getMatt().update();
		f.lep(a.getTabla().getPalya()[3][7]);
		a.getTabla().getMatt().update();
		assertTrue(a.getTabla().getMatt().fekMatt());
	}
	

	@Test
	public void testBeolvas() throws FileNotFoundException {
		int[][] tabla = new int[][]{{1, 2, 3, 4, 5, 3, 2, 1},
			{6, 6, 6, 0, 6, 6, 6, 6},
			{0, 0, 0, 0, 0, 0, 0, 7},
			{0, 0, 0, 6, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, -3, -6, 0, 0, -7},
			{-6, -6, -6, -6, 0, -6, -6, -6},
			{-1, -2, -3, -4, -5, 0, -2, -1}};
		assertArrayEquals(a.getTabla().beolvas("teszt.txt"), tabla);
	}
	
	@Test
	public void testUtes() {
		a.getTabla().getPalya()[1][7].utes(a.getTabla().getPalya()[5][3].getBabu());
		assertEquals(a.getTabla().getPalya()[1][7].getBabu().getClass(), Futo.class);
	}
	
	@Test
	public void testSzabalyosLepesek() {
		List<Mezo> mezok = new LinkedList<Mezo>();
		mezok.add(a.getTabla().getPalya()[4][0]);
		mezok.add(a.getTabla().getPalya()[5][0]);
		assertEquals(a.getTabla().getPalya()[6][0].getBabu().SzabalyosLepesek(a.getTabla()), mezok);
	}
	
	
	@Test
	public void testvanBabu() {
		Mezo m = a.getTabla().getPalya()[7][3];
		Babu v = m.getBabu();
		v.lep(a.getTabla().getPalya()[5][5]);
		a.getTabla().getMatt().update();
		assertFalse(a.getTabla().getPalya()[7][3].vanBabu());
	}
	
	
}

