package nhf;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * A TopListSerialization oszt�ly tesztel�s�hez l�trehozott oszt�ly.
 * @author Sivado
 *
 */
public class TestTopListSerialization {
	TopListSerialization tls;
	
/**
 * A tesztek el�tt l�trehoz egy TopListELementet, majd ebb�l egy TopListSerialization objektumot.	
 */
	@Before
	public void setUp() {
		TopListElement elem = new TopListElement(50, 1, "Tester");
		tls = new TopListSerialization(elem);
		tls.toplist.add(elem);
	}

/**
 * A TopListSerialization oszt�ly writeToFile() met�dus�nak tesztel�se a test.txt f�jlt haszn�lva kimenetk�nt.
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
 * A TopListSerialization oszt�ly readFromFile() met�dus�nak tesztel�se a test2.txt f�jl haszn�lat�val.
 * El�sz�r ebbe a f�jlba ki�rjuk a TopListElementet, amit visszaolvashatunk.
 * @throws IOException
 */
	@Test
	public void readFromFileTest() throws IOException {
		tls.writeToFile("test2.txt");
		tls.readFromFile("test2.txt");
		assertEquals("1. Tester 50 k�r", tls.toplist.get(0).toString());
	}
	
/**
 * A TopListSerialization oszt�ly readFromFile() met�dus�nak tesztel�se hibas.txt f�jllal.
 * Mivel ez kor�bban nem lett l�trehozva, elv�rt az IOException kiv�tel dob�sa.
 * @throws IOException
 */
	@Test(expected = IOException.class)
	public void readFromFileExceptionTest() throws IOException {
		tls.readFromFile("hibas.txt");
	}
/**
 * A TopListSerialization oszt�ly updateFile() met�dus�nak tesztel�se test.txt f�jllal.
 * Erre kor�bban m�r �rtunk ki TopListElementet, amin�l a k�r�k sz�ma t�bb volt, teh�t itt az els� elemnek az �jnak kell lennie.
 * @throws IOException
 */
	@Test
	public void updateFileTest() throws IOException {
		TopListElement elem2 = new TopListElement(40, 0, "Tester2");
		tls.updateFile("test.txt", elem2);
		TopListElement elem = tls.toplist.get(0);
		assertEquals("1. Tester2 40 k�r", elem.toString());
	}
	
/**
 * A TopListSerialization oszt�ly updateFile() met�dus�nak tesztel�se filename.txt f�jllal.
 * A f�jl nem l�tezik, ez�rt IOExceptiont v�runk.
 * @throws IOException
 */
	@Test(expected = IOException.class)
	public void updateFileExceptionTest() throws IOException {
		tls.updateFile("filename.txt", new TopListElement(0, 0, " "));
	}
	
/**
 * A TopListSerialization oszt�ly compareTest() met�dus�nak tesztel�se.
 * A k�l�nb�z� egyenl�s�gek, ill. egyenl�tlens�gek vizsg�lat�hoz 3 TopListElementet hozunk l�tre.
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
