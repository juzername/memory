package nhf;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * A TopListSerialization osztály teszteléséhez létrehozott osztály.
 * @author Sivado
 *
 */
public class TestTopListSerialization {
	TopListSerialization tls;
	
/**
 * A tesztek elõtt létrehoz egy TopListELementet, majd ebbõl egy TopListSerialization objektumot.	
 */
	@Before
	public void setUp() {
		TopListElement elem = new TopListElement(50, 1, "Tester");
		tls = new TopListSerialization(elem);
		tls.toplist.add(elem);
	}

/**
 * A TopListSerialization osztály writeToFile() metódusának tesztelése a test.txt fájlt használva kimenetként.
 * @throws IOException
 * @throws ClassNotFoundException
 */
	@SuppressWarnings("unchecked")
	@Test
	public void writeToFileTest() throws IOException, ClassNotFoundException {
		tls.writeToFile("test.txt");
		FileInputStream fips = new FileInputStream("test.txt");
		ObjectInputStream in = new ObjectInputStream(fips);
		tls.toplist = (ArrayList<TopListElement>) in.readObject();
		assertEquals(50, tls.toplist.get(0).getRound());
		assertEquals(1, tls.toplist.get(0).getPlace());
		assertEquals("Tester", tls.toplist.get(0).getPlayerName());
		in.close();
		}

/**
 * A TopListSerialization osztály readFromFile() metódusának tesztelése a test2.txt fájl használatával.
 * Elõször ebbe a fájlba kiírjuk a TopListElementet, amit visszaolvashatunk.
 * @throws IOException
 */
	@Test
	public void readFromFileTest() throws IOException {
		tls.writeToFile("test2.txt");
		tls.readFromFile("test2.txt");
		assertEquals("1. Tester 50 kör", tls.toplist.get(0).toString());
	}
	
/**
 * A TopListSerialization osztály readFromFile() metódusának tesztelése hibas.txt fájllal.
 * Mivel ez korábban nem lett létrehozva, elvárt az IOException kivétel dobása.
 * @throws IOException
 */
	@Test(expected = IOException.class)
	public void readFromFileExceptionTest() throws IOException {
		tls.readFromFile("hibas.txt");
	}
/**
 * A TopListSerialization osztály updateFile() metódusának tesztelése test.txt fájllal.
 * Erre korábban már írtunk ki TopListElementet, aminél a körök száma több volt, tehát itt az elsõ elemnek az újnak kell lennie.
 * @throws IOException
 */
	@Test
	public void updateFileTest() throws IOException {
		TopListElement elem2 = new TopListElement(40, 0, "Tester2");
		tls.updateFile("test.txt", elem2);
		TopListElement elem = tls.toplist.get(0);
		assertEquals("1. Tester2 40 kör", elem.toString());
	}
	
/**
 * A TopListSerialization osztály updateFile() metódusának tesztelése filename.txt fájllal.
 * A fájl nem létezik, ezért IOExceptiont várunk.
 * @throws IOException
 */
	@Test(expected = IOException.class)
	public void updateFileExceptionTest() throws IOException {
		tls.updateFile("filename.txt", new TopListElement(0, 0, " "));
	}
	
/**
 * A TopListSerialization osztály compareTest() metódusának tesztelése.
 * A különbözõ egyenlõségek, ill. egyenlõtlenségek vizsgálatához 3 TopListElementet hozunk létre.
 */
	@Test
	public void compareTest() {
		TopListElement tl1 = new TopListElement(14, 0, "Player1");
		TopListElement tl2 = new TopListElement(8, 0, "Player2");
		assertEquals(-1, tls.compare(tl1, tl2));
		assertEquals(1, tls.compare(tl2, tl1));
		TopListElement tl3 = new TopListElement(8, 0, "Player3");
		assertEquals(0, tls.compare(tl2, tl3));
	}
}
