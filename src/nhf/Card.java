package nhf;


import java.awt.image.BufferedImage;
/** A Card oszt�ly a j�t�k k�rty�it val�s�tja meg, elt�rolja k�l�nb�z� jellemz�iket, �s getter-setter met�dusokat val�s�t meg.
 * @author Sivado
 */

public class Card {
	private CardType ctype;
	private Habitat habitat;
	private int id;
	private BufferedImage image;
	
/**
 * Be�ll�tja a k�rtya t�pus�t.
 * @param ct a megadott k�rtyat�pus, amire be�ll�t
 */
	public void setCardType(CardType ct) {
		ctype = ct;
	}
	
/**
 * Be�ll�tja a k�rty�n l�v� �llat �l�hely csoportj�t. (Habitat)
 * @param h - a be�ll�tani k�v�nt �l�hely
 */
	public void setHabitat(Habitat h) {
		habitat = h;
	}

/**
 * Be�ll�tja a k�rtya azonos�t�j�t. (id)
 * @param x - a be�lll�tani k�v�nt azonos�t�
 */
	public void setId(int x) {
		id = x;
	}
	
/**
 * Be�ll�tja, hogy milyen k�p tartozik az adott k�rty�hoz. (Nem jelen�t meg, csak elt�rolja BufferedImage t�pusk�nt.)
 * @param im - a be�lll�tani k�v�nt BufferedImage t�pus� k�p
 */
	public void setImage(BufferedImage im) {
		image = im;
	}
	
/**
 * Visszaadja a k�rtya t�pus�t.
 * @return	a k�rtya t�pusa (CardType enumer�ci� eleme)
 */
	public CardType getCardType() {
		return this.ctype;
	}

/**
 * Visszaadja a k�rty�n l�v� �llat besorolt �l�hely�t.
 * @return	a k�rty�n l�v� �llat �l�hely�t (Habitat enumer�ci� tagja)
 */
	public Habitat getHabitat() {
		return this.habitat;
	}
	
/**
 * Visszaadja a k�rtya azonos�t�j�t.
 * @return	a k�rtya azonos�t�ja (1-t�l 6-ig egy term�szetes sz�m), ezt haszn�ljuk majd ahhoz, hogy leellen�rizz�k, hogy a k�rtya p�rj�t tal�ltuk-e meg.
 */
	public int getId() {
		return this.id;
	}
	
/**
 * Visszaadja a k�rty�hoz tartoz� BufferedImage t�pus� k�pet.
 * @return a k�rtya k�pe
 */
	public BufferedImage getImage() {
		return this.image;
	}
	
}
