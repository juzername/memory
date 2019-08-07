package nhf;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * A Game osztály metódusainak tesztelése.
 * @author Sivado
 *
 */
@RunWith(Parameterized.class)
public class GameTest {
	private Game game;
	private GameMode gm;
	private Habitat hab;
	
	public GameTest(GameMode gm, Habitat hab) {
		this.gm = gm;
		this.hab = hab;
	}

	/**
	 * Létrehoz egy új Game játékot, beállítja a játékmódot és az élõhelyet.
	 */
	@Before
	public void setUp() {
		game = new Game();
		game.setGameMode(gm);
		game.setHabitat(hab);
	}
	
/**
 * A createTable() metódus teszelése.
 */
	@Test
	public void testCreateTable() {
		game.createTable();
		Card card4 = game.table.getCardDeck().get(4);
		assertEquals(hab, card4.getHabitat());
		JLabel label = game.table.labelList.get(7);
		assertEquals("card_back.jpg", label.getIcon().toString());
	}
	
/**
 * A Game osztály boolean hasWon() metódusának tesztelése.
 */
	@Test
	public void testHasWon() {
		game.setGainedPairs(5);
		assertFalse("A játéknak még nem kellene véget érnie!", game.hasWon());
		game.setGainedPairs(6);
		assertTrue("A játéknak véget kellett volna érnie!", game.hasWon());
	}

/**
 * A Game osztály endGame() metódusának tesztelése.
 * @throws IOException
 */
	@Test
	public void testEndGame() throws IOException {
		game.tls = new TopListSerialization(new TopListElement(10, 10, ""));
		game.tls.writeToFile("toplist.txt");
		game.setGainedPairs(6);
		game.endGame();
		assertEquals(BorderLayout.class, game.getLayout().getClass());
		assertEquals(JPanel.class, game.endGamePanel.getClass());
		assertEquals("Játék vége", game.endGameLabel.getText());
		assertEquals(JPanel.class, game.gratpanel.getClass());
		assertEquals("Gratulálunk!", game.grat.getText());
		assertEquals(JPanel.class, game.kepPanel.getClass());
		assertEquals("end.jpg", game.kep.getIcon().toString());
		assertEquals(1, game.tls.toplist.get(0).getPlace());
		assertEquals(0, game.tls.toplist.get(0).getRound());
		assertNotNull(game.topListElement);
	}

/**
 * paraméterlista a teszteléshez
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


