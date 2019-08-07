package nhf;

public class Application {
	/**
	 * A main() függvény, ami létrehoz egy NewGame objektumot, aminek meghívja az új játék ablakot inicializáló függv
	 * @param args
	 */
		public static void main(String[] args) {
			NewGame ng = new NewGame();
			ng.initNewGameFrame();
		}
}
