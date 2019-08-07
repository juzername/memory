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
 * A Game oszt�ly a j�t�kunk menet�t bonyol�tja le.
 * Megjelen�ti a k�rty�kat, illetve figyeli �s kezeli az adott k�rty�kra val� kattint�sokat.
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
		setTitle("No� b�rk�ja mem�riaj�t�k");
		createMenu();
	}

/**
 * A createMenu() f�ggv�ny l�trehozza �s megjelen�ti a j�t�khoz tartoz� men�sort, valamint ezekhez az esem�nyeket kezeli.
 * Minden esem�ny kezel�s�hez k�l�n bels� oszt�lyt hoz l�tre a megfelel� f�ggv�ny fel�ldefini�l�s�hoz,
 * kiv�ve az Eredm�nylist�n�l, amihez k�l�n esem�nyfigyel� oszt�ly tartozik. (ActionListenerTopList)
 * 
 */
	public void createMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu jatek, eredmenyek;
		JMenuItem uj_jatek, kilepes, eredmenylista;
		
		jatek = new JMenu("J�t�k");
		eredmenyek = new JMenu("Eredm�nyek");
		uj_jatek = new JMenuItem("�j j�t�k");
		
		uj_jatek.addActionListener(new ActionListener() {
		/**
		 * Ha az �j j�t�k men�pontra kattintunk, megjelenik az �j j�t�k be�ll�t�sait tartalmaz� men�ablak.
		 * Az ActionListener oszt�lyunkban fel�ldefini�ljuk az actionPerformed() f�ggv�nyt.
		 * @param e	az esem�ny, amimek hat�s�ra az �j j�t�k men�ablaka megjelenik (Actionevent)
		 */
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				@SuppressWarnings("unused")
				NewGame ng = new NewGame();
				ng.initNewGameFrame();
			}
		});
		
		kilepes = new JMenuItem("Kil�p�s");
		kilepes.addActionListener(new ActionListener() {
			/**
			 * Ha a Kil�p�s men�pontra kattintunk, bez�r�dik az ablak.
			 * Az ehhez tartoz� ActionListener oszt�lyunkban fel�ldefini�ljuk az actionPerformed() f�ggv�nyt.
			 * @param e	az esem�ny, amimek hat�s�ra bez�r�dik majd az ablak (Actionevent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		eredmenylista = new JMenuItem("Eredm�nylista");
		eredmenylista.addActionListener(new ActionListenerTopList(this));	
		
		menubar.add(jatek);
		menubar.add(eredmenyek);
		jatek.add(uj_jatek);
		jatek.add(kilepes);
		eredmenyek.add(eredmenylista);
		setJMenuBar(menubar);
	}
	
/**
 * A createTable() f�ggv�ny a j�t�kunk k�rty�it inicializ�lja a j�t�k tulajdons�gai alapj�n.
 * A m�r megl�v� j�t�kt�bla k�rtyapaklinak a j�t�kt�pus�t, az �llatok �letter�t �ll�tja be,
 * majd a k�rty�kat �gy inicializ�lja, hogy az els� �s utols� k�rty�k t�pus�t �s az �l�helyet haszn�lja.
 * A "k�rtyapaklit" megkeveri, �s minden k�rty�hoz tartozik egy JLabel a megjelen�t�shez, ezeket a h�tlap k�p�vel t�lti fel.
 * Minden JLabelhez hozz�rendel egy MouseAdaptert, aminek a mouseClicked(MouseEvent e) f�ggv�ny�t fel�l�rja, hogy a mouseClickedHandler() f�ggv�nyt h�vja meg.
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
 * Ez a f�ggv�ny kezeli a k�rty�khoz tartoz� JLabelekre val� eg�rkattint�sok esem�nyeit.
 * Ha els�k�nt r�kattintunk valamire, akkor megjelen�ti a k�p�t, �s minden p�ros kattint�sn�l a k�p megjelen�t�se ut�n
 * k�l�nb�z� eseteket vizsg�l:
 * ha ugyanarra k�tszer kattintunk, akkor marad minden, ha a k�t k�p egy p�rt alkot (az id v�ltoz�juk egyenl�),
 * akkor f�l m�sodperces k�sleltet�s ut�n elt�nnek, �s az �sszegy�jt�tt p�rok sz�ma eggyel n�. Ha a j�t�kot megnyerte, akkor az endGame() f�ggv�nyt h�vja meg.
 * Egy�b esetben f�l perc k�sleltet�s ut�n a k�rty�k h�tlapj�t jelen�ti meg.
 * Minden m�sodik �rv�nyes kattint�s ut�n a j�t�k k�reinek sz�m�t inkrement�lja.
 * @param e - Az a MouseEvent, vagyis eg�rkattint�s, aminek a hat�s�t vizsg�ljuk.
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
 * A hasWon() f�ggv�ny azt vizsg�lja, hogy az �sszegy�jt�tt p�rok sz�ma el�rte-e e az �sszes k�rtyap�r sz�m�t.
 * @return	true, ha �sszegy�lt
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
 * Az endGame() f�ggv�ny a j�t�k v�g�n megjelen� ablak kialak�t�s��rt felel�s,
 * valamint a j�t�k v�g�n friss�ti a leg�jabb eredm�nnyel az eredm�nylista f�jlj�t.
 * Egy BorderLayout fels� r�sz�n megjelenik	a "J�t�k v�ge" felirat, k�z�pen egy k�p, �s lent egy "Gratul�lunk!" felirat.
 */
	public void endGame() {
			setLayout(new BorderLayout());
			
			endGamePanel = new JPanel();
			endGameLabel = new JLabel("J�t�k v�ge");
			
			gratpanel = new JPanel();
			grat = new JLabel("Gratul�lunk!");
			
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
 * Visszaadja, hogy a j�t�kban h�ny k�r ment le.
 * @return a k�r�k sz�ma (int)
 */
	public int getNumClick() {
		return numClick;
	}

/** Visszaaadja, hogy a j�t�kban h�ny �rv�nyes kattint�s volt.
 * @return	kattint�sok sz�ma (int)
 */
	public int getRound() {
		return round;
	}

/**
 * Visszaadja a j�t�kos nev�t.
 * @return j�t�kosn�v (String)
 */
	public String getPlayer() {
		return player;
	}

/**
 * Visszaadja az �sszegy�jt�tt k�rtyap�rok sz�m�t.
 * @return	�sszegy�jt�tt p�rok sz�ma
 */
	public int getGainedPairs() {
		return gainedPairs;
	}

/**
 * Visszaadja a j�t�km�dot, hogy mem�riaj�t�kot, vagy sz�k�rty�s j�t�kot j�tszik a felhaszn�l�.
 * @return  j�t�km�d
 */
	public GameMode getGameMode() {
		return gamemode;
	}
	
/**
 * Visszaadja a j�t�k k�rt�yin szerepl� �llatcsoportot. (Az �l�helyet.)
 * @return	�l�hely
 */
	public Habitat getHabitat() {
		return habitat;
	}
	
/**
 * Be�ll�tja a j�t�kban a k�r�k sz�m�t
 * @param x ennyi k�rt �ll�tunk be.
 */
	public void setRound(int x) {
		round = x;
	}
	
/**
 * Be�ll�tja a j�t�kban a kattint�sok sz�m�t
 * @param x	ennyi sz�m� kattint�st �ll�t be
 */
	public void setNumClick(int x) {
		numClick = x;
	}
	
/**
 * Be�ll�tja a j�t�kos nev�t
 * @param p	 a be�ll�tani k�v�nt j�t�kosn�v
 */
	public void setPlayer(String p) {
		player = p;
	}

/**
 * Be�ll�tja az �sszegy�jt�tt p�rok sz�m�t.
 * @param x	ennyire �ll�tja be az �sszegy�lt p�rok sz�m�t
 */
	public void setGainedPairs(int x) {
		gainedPairs = x;
	}
	
/**
 * Be�ll�tja a j�t�km�dot (Mem�riaj�t�k vagy Sz�k�rtyaj�t�k)
 * @param gm	a be�ll�tani k�v�nt j�t�km�d
 */
	public void setGameMode(GameMode gm) {
		gamemode = gm;
	}
	
/**
 * Be�ll�tja a k�rty�kon szerepl� �llatcsoport �l�hely�t, amely szerint csoportos�tva vannak.
 * @param h	a be�ll�tani k�v�nt �l�hely
 */
	public void setHabitat(Habitat h) {
		habitat = h;
	}
}
