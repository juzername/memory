package nhf;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * TableTest osztály a Table osztály metódusainak teszteléséhez.
 * @author Lenovo
 *
 */
@RunWith(Parameterized.class)
public class TableTest {
	Table table;
	GameMode gamemode;
	Habitat hab;
	
	public TableTest(GameMode gamemode, Habitat hab) {
		this.gamemode = gamemode;
		this.hab = hab;
	}
/**
 * új tábla létrehozása
 */
	@Before
	public void setUp() {
		table = new Table();
	}
/**
 * a Table osztály initCardsByGameMode metódusának tesztelése. A kártyatípusokat vizsgálja a játékmódok alapján.
 */
	@Test
	public void testInitCardsByGameMode() {
		table.initCardsByGameMode(gamemode);
		if(gamemode.equals(GameMode.MEMORYGAME)) {
			for(int i = 0; i < table.getCardDeck().size(); i++) 
				assertEquals(CardType.MEMORYCARD, table.getCardDeck().get(i).getCardType());
		}
		else {
			for(int i = 0; i < 6; i++) 
				assertEquals(CardType.IMAGECARD, table.getCardDeck().get(1).getCardType());
			for(int i = 6; i < 12; i++)
				assertEquals(CardType.TEXTCARD, table.getCardDeck().get(7).getCardType());
		}
	}
	
/**
 * a Table osztály initCardsByHabitats() metódusának tesztelése.
 */
	@Test
	public void testInintCardsByHabitats() {
		table.initCardsByHabitats(hab);
		for(int i = 0; i < table.getCardDeck().size(); i++) {
		assertEquals(hab, table.getCardDeck().get(i).getHabitat());
		}
	}

/**
 * a Table osztály initCards() metódusának tesztelése.
 */
	@Test
	public void testInitCards() throws IOException {
		table.initCardsByGameMode(gamemode);
		table.initCardsByHabitats(hab);
		BufferedImage bim = null;

		if(gamemode.equals(GameMode.MEMORYGAME)) {
			table.initCards(CardType.MEMORYCARD, CardType.MEMORYCARD, hab);
			Card c8 = table.getCardDeck().get(8);
			assertEquals(hab, c8.getHabitat());
			assertEquals(2, c8.getId());
			bim = new BufferedImage(178, 250, ImageIO.read(new File("MEMORYCARD"+"-"+hab.toString()+"-2.png")).getType());
			assertEquals(bim.getWidth(), c8.getImage().getWidth());
			assertEquals(bim.getHeight(), c8.getImage().getHeight());
			Assert.assertArrayEquals(bim.getPropertyNames(), c8.getImage().getPropertyNames());

		}	
		else {
			table.initCards(CardType.IMAGECARD, CardType.TEXTCARD, hab);
			Card c1 = table.getCardDeck().get(1);
			assertEquals(CardType.IMAGECARD, c1.getCardType());
			assertEquals(hab, c1.getHabitat());
			assertEquals(1, c1.getId());
			bim = new BufferedImage(178, 250, ImageIO.read(new File("IMAGECARD"+"-"+hab.toString()+"-1.png")).getType());
			assertEquals(bim.getWidth(), c1.getImage().getWidth());
			assertEquals(bim.getHeight(), c1.getImage().getHeight());
			assertEquals(bim.getClass(), c1.getImage().getClass());
			
			Card c8 = table.getCardDeck().get(8);
			assertEquals(hab, c8.getHabitat());
			assertEquals(2, c8.getId());
			bim = new BufferedImage(178, 250, ImageIO.read(new File("TEXTCARD"+"-"+hab.toString()+"-2.png")).getType());
			assertEquals(bim.getWidth(), c8.getImage().getWidth());
			assertEquals(bim.getHeight(), c8.getImage().getHeight());
			assertEquals(bim.getClass(), c8.getImage().getClass());
		}
	}
/**
 * Paraméterlista a tesztesetekhez.
 * @return	paraméterlista
 */
	@Parameters
	public static List<Object[]> parameters(){
		List<Object[]> params = new ArrayList<>();
		params.add(new Object[] {GameMode.MEMORYGAME, Habitat.FARM});
		params.add(new Object[] {GameMode.WORDCARDSGAME, Habitat.JUNGLE});
		params.add(new Object[] {GameMode.MEMORYGAME, Habitat.PET});
		params.add(new Object[] {GameMode.WORDCARDSGAME, Habitat.RIVER});
		params.add(new Object[] {GameMode.MEMORYGAME, Habitat.SAFARI});
		return params;
	}

}
