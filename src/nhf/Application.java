package nhf;

public class Application {
	/**
	 * A main() f�ggv�ny, ami l�trehoz egy NewGame objektumot, aminek megh�vja az �j j�t�k ablakot inicializ�l� f�ggv
	 * @param args
	 */
		public static void main(String[] args) {
			NewGame ng = new NewGame();
			ng.initNewGameFrame();
		}
}
