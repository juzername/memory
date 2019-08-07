package nhf;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A Table oszt�ly a j�t�kt�bla kialak�t�s��rt felel. A JFrame lesz�rmazottja, mert megjelen�t egy ablakot.
 * Tagv�ltoz�i egy k�rty�kb�l (Card) �ll�, 12 elem� ArrayList, a k�rty�k logikai kezel�s�hez,
 * �s egy JLabeleket tartalmaz�, amely a megjelen�t�s �rdek�ben majd a k�rty�k hely�t mutatja.
 */
@SuppressWarnings("serial")
public class Table extends JFrame{
	public List<JLabel> labelList;
	private List<Card> cardDeck;
	
	public Table() {
		cardDeck = new ArrayList<Card>(12);
		
		for(int i = 0; i < 12; i++) {
			cardDeck.add(new Card());
		}
		
		labelList = new ArrayList<JLabel>(12);
		
		for(int i = 0; i < 12; i++) {
			labelList.add(new JLabel());
		}
		
		initCardIDs();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(1800, 700);
	}
	
/**
 * Az initCardIDs() f�ggv�ny a k�rtyapakli 12 elem�t t�lti fel �gy, hogy az els� hatot, illetve az utols� hatot is 1-6-ig n�vekv� sorrendben.
 * Ennek az a c�lja, hogy az id alapj�n meg lehessen hat�rozni, hogy 2 k�rtya p�rban van-e.	
 */
	public void initCardIDs() {
		
		for(int i = 0; i < cardDeck.size()/2; i++) {
			Card c = cardDeck.get(i);
			c.setId(i);
		}
		
		for(int i = cardDeck.size()/2; i< cardDeck.size(); i++) {
			Card c = cardDeck.get(i);
			c.setId(i-6);
		}
	}
	
/**
 * Ez a f�ggv�ny a k�rty�k t�pus�t inicializ�lja a j�t�km�d alapj�n.
 * Ha mem�riaj�t�kot j�tszunk, az �sszes k�rtya mem�riak�rtya.
 * Sz�k�rtya j�t�km�d eset�n az els� fele csak k�pet tartalmaz (IMAGECARD), m�g a m�sik fele csak az angol sz�t (TEXTCARD).
 * @param gm	ez alapj�n a j�t�km�d alapj�n �ll�tjuk be a k�rty�k t�pus�t
 */
	public void initCardsByGameMode(GameMode gm) {
		
		switch(gm) {
		case MEMORYGAME:
			for(int i = 0; i < cardDeck.size(); i++) {
					Card c = cardDeck.get(i);
					c.setCardType(CardType.MEMORYCARD);
					cardDeck.set(i, c);
					}
			break;
	
		case WORDCARDSGAME:
			for(int i = 0; i < cardDeck.size()/2; i++) {
				Card c = cardDeck.get(i);
				c.setCardType(CardType.IMAGECARD);
				cardDeck.set(i, c);
			}
		
			for(int i = cardDeck.size()/2; i < cardDeck.size(); i++) {
				Card c = cardDeck.get(i);
				c.setCardType(CardType.TEXTCARD);
				cardDeck.set(i, c);
			}
			break;
		}
	}
	
/**
 * Ez a f�ggv�ny az �l�hely alapj�n inicializ�lja a k�rty�kat (logikailag).
 * @param h	az �l�hely, amit a k�rty�knak be�ll�tunk
 */
	public void initCardsByHabitats(Habitat h) {
		for(int i = 0; i < cardDeck.size(); i++) {
			Card c = cardDeck.get(i);
			switch(h) {
			case FARM:
				c.setHabitat(Habitat.FARM);
				break;
			case JUNGLE:
				c.setHabitat(Habitat.JUNGLE);
				break;
			case PET:
				c.setHabitat(Habitat.PET);
				break;
			case RIVER:
				c.setHabitat(Habitat.RIVER);
				break;
			case SAFARI:
				c.setHabitat(Habitat.SAFARI);
				break;
			}
		}
	}
	
/**
 * Ez a f�ggv�ny a k�rty�khoz BufferedImage t�pus� k�pet rendel. Ez hat�rozza meg, hogy a k�rtya t�nylegesen hogy n�z ki.
 * Azt haszn�ljuk fel, hogy az ImageIO-r�l beolvasott k�pek neve a k�rty�k t�pus�t, az �l�helyet �s az id-t tartalmazz�k.
 * A k�peket egys�gesen 178x250 m�ret�re �tm�retezz�k egy �j BufferedImage seg�ts�g�vel.
 * Fontos, hogy mivel sz�k�rtyaj�t�k eset�n a k�rty�k els� �s m�sodik fele m�s k�rtyat�pus�, az inicializ�l�shoz az els� �s az utols� elem k�rtyat�pus�t tekintj�k.
 * @param ct1	k�v�nt k�rtyat�pus az elej�n
 * @param ct2	k�v�nt k�rtyat�pus a v�g�n
 * @param hab	k�v�nt �l�hely
 */
	public void initCards(CardType ct1, CardType ct2, Habitat hab) {
		for(int i = 0; i < cardDeck.size()/2; i++) {
			Card card = cardDeck.get(i);
			try {
				BufferedImage bim = ImageIO.read(new File(ct1.toString()+"-"+hab.toString()+"-"+ Integer.toString(i)+".png"));
				BufferedImage nbim = new BufferedImage(178, 250, bim.getType());
				Graphics2D g2d = nbim.createGraphics();
				g2d.drawImage(bim, 0, 0, 178, 250, null);
				g2d.dispose();
				card.setImage(nbim);
				cardDeck.set(i, card);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = cardDeck.size()/2; i < cardDeck.size(); i++) {
			Card card = cardDeck.get(i);
			try {
				BufferedImage bim = ImageIO.read(new File(ct2.toString()+"-"+hab.toString()+"-"+ Integer.toString(i-6)+".png"));
				BufferedImage nbim = new BufferedImage(178, 250, bim.getType());
				Graphics2D g2d = nbim.createGraphics();
				g2d.drawImage(bim, 0, 0, 178, 250, null);
				g2d.dispose();
				card.setImage(nbim);
				cardDeck.set(i, card);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

/**
 * Visszaadja a k�rtyapaklit, ami egy Card t�pus� objektumokb�l �ll� ArrayList.
 * @return	k�rtyapakli lista
 */
	public List<Card> getCardDeck() {
		return cardDeck;
	}
	
/**
 * Visszaadja a JLabeleket tartalmaz� ArrayListet, azaz a labelListet.
 * @return	JLabelek list�ja
 */
	public List<JLabel> getLabelList() {
		return labelList;
	}	

}
