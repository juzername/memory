package nhf;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * A Game osztály a játékunk menetét bonyolítja le.
 * Megjeleníti a kártyákat, illetve figyeli és kezeli az adott kártyákra való kattintásokat.
 * @author Sivado
 *
 */
@SuppressWarnings("serial")
public class Game extends JFrame{
	Table table;
	private int numClick;
	private int currentidx;
	private int oddidx;
	private JPanel tablepanel;
	private Timer timer;
	private int round = 0;
	private int gainedPairs = 0;
	String player;
	TopListElement topListElement;
	TopListSerialization tls;
	private GameMode gamemode;
	private Habitat habitat;
	
	public Game(){
		table = new Table();
		setSize(1800, 700);
		setTitle("Noé bárkája memóriajáték");
		createMenu();
	}

/**
 * A createMenu() függvény létrehozza és megjeleníti a játékhoz tartozó menüsort, valamint ezekhez az eseményeket kezeli.
 * Minden esemény kezeléséhez külön belsõ osztályt hoz létre a megfelelõ függvény felüldefiniálásához,
 * kivéve az Eredménylistánál, amihez külön eseményfigyelõ osztály tartozik. (ActionListenerTopList)
 * 
 */
	public void createMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu jatek, eredmenyek;
		JMenuItem uj_jatek, kilepes, eredmenylista;
		
		jatek = new JMenu("Játék");
		eredmenyek = new JMenu("Eredmények");
		uj_jatek = new JMenuItem("Új játék");
		
		uj_jatek.addActionListener(new ActionListener() {
		/**
		 * Ha az Új játék menüpontra kattintunk, megjelenik az új játék beállításait tartalmazó menüablak.
		 * Az ActionListener osztályunkban felüldefiniáljuk az actionPerformed() függvényt.
		 * @param e	az esemény, amimek hatására az új játék menüablaka megjelenik (Actionevent)
		 */
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				@SuppressWarnings("unused")
				NewGame ng = new NewGame();
				ng.initNewGameFrame();
			}
		});
		
		kilepes = new JMenuItem("Kilépés");
		kilepes.addActionListener(new ActionListener() {
			/**
			 * Ha a Kilépés menüpontra kattintunk, bezáródik az ablak.
			 * Az ehhez tartozó ActionListener osztályunkban felüldefiniáljuk az actionPerformed() függvényt.
			 * @param e	az esemény, amimek hatására bezáródik majd az ablak (Actionevent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		eredmenylista = new JMenuItem("Eredménylista");
		eredmenylista.addActionListener(new ActionListenerTopList(this));	
		
		menubar.add(jatek);
		menubar.add(eredmenyek);
		jatek.add(uj_jatek);
		jatek.add(kilepes);
		eredmenyek.add(eredmenylista);
		setJMenuBar(menubar);
	}
	
/**
 * A createTable() függvény a játékunk kártyáit inicializálja a játék tulajdonságai alapján.
 * A már meglévõ játéktábla kártyapaklinak a játéktípusát, az állatok életterét állítja be,
 * majd a kártyákat úgy inicializálja, hogy az elsõ és utolsó kártyák típusát és az élõhelyet használja.
 * A "kártyapaklit" megkeveri, és minden kártyához tartozik egy JLabel a megjelenítéshez, ezeket a hátlap képével tölti fel.
 * Minden JLabelhez hozzárendel egy MouseAdaptert, aminek a mouseClicked(MouseEvent e) függvényét felülírja, hogy a mouseClickedHandler() függvényt hívja meg.
 */
	public void createTable() {
		List<Card> cardDeck = table.getCardDeck();
		List<JLabel> labelList = table.getLabelList();

		table.initCardsByGameMode(gamemode);
		table.initCardsByHabitats(habitat);
		table.initCards(cardDeck.get(0).getCardType(), cardDeck.get(cardDeck.size()-1).getCardType(), cardDeck.get(0).getHabitat());
		Collections.shuffle(cardDeck);
		
		tablepanel = new JPanel(new GridLayout(2,6));
				
		for(int i = 0; i < labelList.size(); i++) {
			JLabel label = labelList.get(i);
			label = new JLabel(new ImageIcon("card_back.jpg"));
			
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked( MouseEvent e ){
					mouseClickedHandler(e);}
			});

			
			labelList.set(i, label);
			tablepanel.add(label);
		}
		add(tablepanel);
		setVisible(true);
		
	}

/**
 * Ez a függvény kezeli a kártyákhoz tartozó JLabelekre való egérkattintások eseményeit.
 * Ha elsõként rákattintunk valamire, akkor megjeleníti a képét, és minden páros kattintásnál a kép megjelenítése után
 * különbözõ eseteket vizsgál:
 * ha ugyanarra kétszer kattintunk, akkor marad minden, ha a két kép egy párt alkot (az id változójuk egyenlõ),
 * akkor fél másodperces késleltetés után eltûnnek, és az összegyûjtött párok száma eggyel nõ. Ha a játékot megnyerte, akkor az endGame() függvényt hívja meg.
 * Egyéb esetben fél perc késleltetés után a kártyák hátlapját jeleníti meg.
 * Minden második érvényes kattintás után a játék köreinek számát inkrementálja.
 * @param e - Az a MouseEvent, vagyis egérkattintás, aminek a hatását vizsgáljuk.
 */
	private void mouseClickedHandler(MouseEvent e) {
		List<Card> cardDeck = table.getCardDeck();
		List<JLabel> labelList = table.getLabelList();
		numClick++;

		timer = new Timer();
		
		for(int i = 0; i < labelList.size(); i++) 
			if(e.getSource()==labelList.get(i)) {
				currentidx = i;
			}
		labelList.get(currentidx).setIcon(new ImageIcon(cardDeck.get(currentidx).getImage()));
		
		
			if(numClick % 2 == 0) {
				labelList.get(currentidx).setIcon(new ImageIcon(cardDeck.get(currentidx).getImage()));
				labelList.get(oddidx).setIcon(new ImageIcon(cardDeck.get(oddidx).getImage()));
				
				if(currentidx == oddidx) {
					numClick--;
					return;
				}
				else {
					round++;
					
					if(cardDeck.get(currentidx).getId() == cardDeck.get(oddidx).getId()) { 							
						TimerTask timertaskDisable = new TimerTask() {
							public void run(){
								labelList.get(currentidx).setVisible(false);
								labelList.get(oddidx).setVisible(false);
								gainedPairs++;
								
								if (hasWon())
									endGame();
							}
						};
						timer.schedule(timertaskDisable, 500);
					}
					else{

						TimerTask timertaskTurn = new TimerTask() {
							public void run(){
								labelList.get(currentidx).setIcon(new ImageIcon("card_back.jpg"));
								labelList.get(oddidx).setIcon(new ImageIcon("card_back.jpg"));
							}
						};
						timer.schedule(timertaskTurn, 500);
					}
				}
			}
			else{
				oddidx = currentidx;
			}	
	}
	
/*
 * A hasWon() függvény azt vizsgálja, hogy az összegyûjtött párok száma elérte-e e az összes kártyapár számát.
 * @return	true, ha összegyûlt
 */
	public boolean hasWon() {
		return gainedPairs == table.getCardDeck().size()/2;
	}
	
	JPanel endGamePanel;
	JLabel endGameLabel;
	JPanel gratpanel;
	JLabel grat;
	JPanel kepPanel;
	JLabel kep;

/**
 * Az endGame() függvény a játék végén megjelenõ ablak kialakításáért felelõs,
 * valamint a játék végén frissíti a legújabb eredménnyel az eredménylista fájlját.
 * Egy BorderLayout felsõ részén megjelenik	a "Játék vége" felirat, középen egy kép, és lent egy "Gratulálunk!" felirat.
 */
	public void endGame() {
			setLayout(new BorderLayout());
			
			endGamePanel = new JPanel();
			endGameLabel = new JLabel("Játék vége");
			
			gratpanel = new JPanel();
			grat = new JLabel("Gratulálunk!");
			
			endGameLabel.setFont(new Font("Broadway", Font.PLAIN, 48));
			grat.setFont(new Font("Broadway", Font.PLAIN, 48));
			
			endGamePanel.add(endGameLabel);
			gratpanel.add(grat);
			
			kepPanel = new JPanel();
			kep = new JLabel(new ImageIcon("end.jpg"));
			kepPanel.add(kep);
			
			add(endGamePanel, BorderLayout.NORTH);
			add(gratpanel, BorderLayout.SOUTH);
			add(kepPanel, BorderLayout.CENTER);
			
			setVisible(true);
			
			topListElement = new TopListElement(round, 1, player);
			tls = new TopListSerialization(topListElement);
			tls.updateFile("toplist.txt", topListElement);
	}
	

/**
 * Visszaadja, hogy a játékban hány kör ment le.
 * @return a körök száma (int)
 */
	public int getNumClick() {
		return numClick;
	}

/** Visszaaadja, hogy a játékban hány érvényes kattintás volt.
 * @return	kattintások száma (int)
 */
	public int getRound() {
		return round;
	}

/**
 * Visszaadja a játékos nevét.
 * @return játékosnév (String)
 */
	public String getPlayer() {
		return player;
	}

/**
 * Visszaadja az összegyûjtött kártyapárok számát.
 * @return	összegyûjtött párok száma
 */
	public int getGainedPairs() {
		return gainedPairs;
	}

/**
 * Visszaadja a játékmódot, hogy memóriajátékot, vagy szókártyás játékot játszik a felhasználó.
 * @return  játékmód
 */
	public GameMode getGameMode() {
		return gamemode;
	}
	
/**
 * Visszaadja a játék kártáyin szereplõ állatcsoportot. (Az élõhelyet.)
 * @return	élõhely
 */
	public Habitat getHabitat() {
		return habitat;
	}
	
/**
 * Beállítja a játékban a körök számát
 * @param x ennyi kört állítunk be.
 */
	public void setRound(int x) {
		round = x;
	}
	
/**
 * Beállítja a játékban a kattintások számát
 * @param x	ennyi számú kattintást állít be
 */
	public void setNumClick(int x) {
		numClick = x;
	}
	
/**
 * Beállítja a játékos nevét
 * @param p	 a beállítani kívánt játékosnév
 */
	public void setPlayer(String p) {
		player = p;
	}

/**
 * Beállítja az összegyûjtött párok számát.
 * @param x	ennyire állítja be az összegyûlt párok számát
 */
	public void setGainedPairs(int x) {
		gainedPairs = x;
	}
	
/**
 * Beállítja a játékmódot (Memóriajáték vagy Szókártyajáték)
 * @param gm	a beállítani kívánt játékmód
 */
	public void setGameMode(GameMode gm) {
		gamemode = gm;
	}
	
/**
 * Beállítja a kártyákon szereplõ állatcsoport élõhelyét, amely szerint csoportosítva vannak.
 * @param h	a beállítani kívánt élõhely
 */
	public void setHabitat(Habitat h) {
		habitat = h;
	}
}
