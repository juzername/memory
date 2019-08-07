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
 * A Table osztály a játéktábla kialakításáért felel. A JFrame leszármazottja, mert megjelenít egy ablakot.
 * Tagváltozói egy kártyákból (Card) álló, 12 elemû ArrayList, a kártyák logikai kezeléséhez,
 * és egy JLabeleket tartalmazó, amely a megjelenítés érdekében majd a kártyák helyét mutatja.
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
 * Az initCardIDs() függvény a kártyapakli 12 elemét tölti fel úgy, hogy az elsõ hatot, illetve az utolsó hatot is 1-6-ig növekvõ sorrendben.
 * Ennek az a célja, hogy az id alapján meg lehessen határozni, hogy 2 kártya párban van-e.	
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
 * Ez a függvény a kártyák típusát inicializálja a játékmód alapján.
 * Ha memóriajátékot játszunk, az összes kártya memóriakártya.
 * Szókártya játékmód esetén az elsõ fele csak képet tartalmaz (IMAGECARD), míg a másik fele csak az angol szót (TEXTCARD).
 * @param gm	ez alapján a játékmód alapján állítjuk be a kártyák típusát
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
 * Ez a függvény az élõhely alapján inicializálja a kártyákat (logikailag).
 * @param h	az élõhely, amit a kártyáknak beállítunk
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
 * Ez a függvény a kártyákhoz BufferedImage típusú képet rendel. Ez határozza meg, hogy a kártya ténylegesen hogy néz ki.
 * Azt használjuk fel, hogy az ImageIO-ról beolvasott képek neve a kártyák típusát, az élõhelyet és az id-t tartalmazzák.
 * A képeket egységesen 178x250 méretûre átméretezzük egy új BufferedImage segítségével.
 * Fontos, hogy mivel szókártyajáték esetén a kártyák elsõ és második fele más kártyatípusú, az inicializáláshoz az elsõ és az utolsó elem kártyatípusát tekintjük.
 * @param ct1	kívánt kártyatípus az elején
 * @param ct2	kívánt kártyatípus a végén
 * @param hab	kívánt élõhely
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
 * Visszaadja a kártyapaklit, ami egy Card típusú objektumokból álló ArrayList.
 * @return	kártyapakli lista
 */
	public List<Card> getCardDeck() {
		return cardDeck;
	}
	
/**
 * Visszaadja a JLabeleket tartalmazó ArrayListet, azaz a labelListet.
 * @return	JLabelek listája
 */
	public List<JLabel> getLabelList() {
		return labelList;
	}	

}
