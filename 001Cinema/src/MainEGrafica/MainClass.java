package MainEGrafica;
import gestioneDegliEventi.Gestione;


public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Finestra fin = new Finestra("Gestione dei cinema");
		fin.addWindowListener(new Gestione(fin));
		fin.setVisible(true);
	}
}
