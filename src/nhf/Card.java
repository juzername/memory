package nhf;


import java.awt.image.BufferedImage;
/** A Card osztály a játék kártyáit valósítja meg, eltárolja külünbüzõ jellemzõiket, és getter-setter metódusokat valósít meg.
 * @author Sivado
 */

public class Card {
	private CardType ctype;
	private Habitat habitat;
	private int id;
	private BufferedImage image;
	
/**
 * Beállítja a kártya típusát.
 * @param ct a megadott kártyatípus, amire beállít
 */
	public void setCardType(CardType ct) {
		ctype = ct;
	}
	
/**
 * Beállítja a kártyán lévõ állat élõhely csoportját. (Habitat)
 * @param h - a beállítani kívánt élõhely
 */
	public void setHabitat(Habitat h) {
		habitat = h;
	}

/**
 * Beállítja a kártya azonosítóját. (id)
 * @param x - a beálllítani kívánt azonosító
 */
	public void setId(int x) {
		id = x;
	}
	
/**
 * Beállítja, hogy milyen kép tartozik az adott kártyához. (Nem jelenít meg, csak eltárolja BufferedImage típusként.)
 * @param im - a beálllítani kívánt BufferedImage típusú kép
 */
	public void setImage(BufferedImage im) {
		image = im;
	}
	
/**
 * Visszaadja a kártya típusát.
 * @return	a kártya típusa (CardType enumeráció eleme)
 */
	public CardType getCardType() {
		return this.ctype;
	}

/**
 * Visszaadja a kártyán lévõ állat besorolt élõhelyét.
 * @return	a kártyán lévõ állat élõhelyét (Habitat enumeráció tagja)
 */
	public Habitat getHabitat() {
		return this.habitat;
	}
	
/**
 * Visszaadja a kártya azonosítóját.
 * @return	a kártya azonosítója (1-tõl 6-ig egy természetes szám), ezt használjuk majd ahhoz, hogy leellenõrizzük, hogy a kártya párját találtuk-e meg.
 */
	public int getId() {
		return this.id;
	}
	
/**
 * Visszaadja a kártyához tartozó BufferedImage típusú képet.
 * @return a kártya képe
 */
	public BufferedImage getImage() {
		return this.image;
	}
	
}
