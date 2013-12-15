package gestioneDegliEventi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import MainEGrafica.Finestra;


public class GestioneCambioTabPage implements ChangeListener {
	
	private Finestra f = null;
	
	/**
	 * Costruttore al quale viene passata una finestra
	 * @param fin
	 */
	public GestioneCambioTabPage(Finestra fin){
		f = fin;
	}
	

	
	/**
	 * Funzione che prende da una tabella un solo valore data la chiave primaria
	 * @param nomeCampoPrimario Nome del campo contenente la chiave primaria della tabella da cui si vuole pescare
	 * @param chiaveEsterna Nome del campo contenente la chiave esterna
	 * @param nomeCampoDaPrendere Nome del campo che si vuole recuperare
	 * @param nomeTab Tabella da cui si vuole recuperare il valore
	 * @return
	 */
	private String recuperaChiavePrimaria(String nomeCampoPrimario, String chiaveEsterna, String nomeCampoDaPrendere, String nomeTab){
		PreparedStatement stmSql = null;
		ResultSet rs = null;
		String valoreRestituito = null;
		
		try {
			stmSql = f.conn.prepareStatement("select " + nomeCampoDaPrendere + " from " + nomeTab + " where " + nomeCampoPrimario + " = " + chiaveEsterna);
			rs = stmSql.executeQuery();
			if (rs.next())	valoreRestituito = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valoreRestituito;
	}
	
	
	/**
	 * Data una tabella qualunque essa viene riempita.
	 * @param tab Il default table model relativo alla tabella
	 * @param nomeTab il nome della tabella su database
	 * @param lenght il numero di colonne
	 */
	private void riempimentoTabella(DefaultTableModel tab, String nomeTab, int lenght){
		// istruzioni per la query
		while( tab.getRowCount() > 0 ) tab.removeRow(0);
		PreparedStatement stmSql = null;
		ResultSet rs = null;
		int x = 1;  // usato per riempire le righe
		
		try {
			// prelevo tutto da una data tabella
			stmSql = f.conn.prepareStatement("select * from " + nomeTab);
			rs = stmSql.executeQuery();
			
			while(rs.next()){
				Vector<String> riga = new Vector<String>();
				x = 1;
				
				while(x <= lenght){

					// questo switch � utile per tabelle come proiezioni o recita in cui
					// vi sono chiavi esterne da sostituire con il valore alfanumerico
					// della rispettiva tabella
					switch(nomeTab){
						case "recita":
							if(x == 2){  // attore
								riga.add(recuperaChiavePrimaria("CodAttore", rs.getString(x), "Nome", "attori"));
							}else if(x == 3){  // film
								riga.add(recuperaChiavePrimaria("CodFilm", rs.getString(x), "Titolo", "film"));
							}else{  // campo normale
								riga.add(rs.getString(x));
							}
							break;
						case "proiezioni":
							if(x == 2){  // film
								riga.add(recuperaChiavePrimaria("CodFilm", rs.getString(x), "Titolo", "film"));
							}else if(x == 3){  // sala
								riga.add(recuperaChiavePrimaria("CodSala", rs.getString(x), "Nome", "sale"));
							}else{  // campo normale
								riga.add(rs.getString(x));
							}
							break;
						// se la tabella non � ne recita ne proiezioni la riempo
						// senza andare a pescare valori da altre tabelle
						default:
							riga.add(rs.getString(x));
					}
					x++;  // incremento della x per passare al prox elemento di rs
				}
				tab.addRow(riga);  // aggiunta della riga al tabelmodel
			}
		} catch (SQLException e) {
			System.out.println("Errore nella richiesta della query");
		}
	}

	/**
	 * Funzione overraidata che viene evocata ogni volta che si cambia tab
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// controllo a quale scheda l'utente si � spostato
		if(((JTabbedPane) arg0.getSource()).getSelectedIndex() == 0){  // film
			riempimentoTabella(f.getDtmFilm(), "film", 6);
		}else if(((JTabbedPane) arg0.getSource()).getSelectedIndex() == 1){  // attori
			riempimentoTabella(f.getDtmAttori(), "attori", 4);			
		}else if(((JTabbedPane) arg0.getSource()).getSelectedIndex() == 2){  // recita
			riempimentoTabella(f.getDtmRecita(), "recita", 3);
			// pulizia delle combo attori e film
			while(f.getCmbAttori().getItemCount() > 0) f.getCmbAttori().removeItemAt(0);
			while(f.getCmbFilmRecita().getItemCount() > 0) f.getCmbFilmRecita().removeItemAt(0);

			 // aggiunta degli elementi alla combo attori e film
			 PreparedStatement stmSql = null;
			 ResultSet rs = null;
			 
			 try {
				 stmSql = f.conn.prepareStatement("select CodAttore, Nome from attori");
				 rs = stmSql.executeQuery();				 
				 while(rs.next()){
					 f.getCmbAttori().addItem(rs.getString("CodAttore") + "|" + rs.getString("Nome"));
				 }
				 
				 stmSql = f.conn.prepareStatement("select CodFilm, Titolo from film");
				 rs = stmSql.executeQuery();				 
				 while(rs.next()){
					 f.getCmbFilmRecita().addItem(rs.getString("CodFilm") + "|" + rs.getString("Titolo"));
				 }	 
			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}else if(((JTabbedPane) arg0.getSource()).getSelectedIndex() == 3){  // sale
			riempimentoTabella(f.getDtmSale(), "sale", 4);		
		}else if(((JTabbedPane) arg0.getSource()).getSelectedIndex() == 4){  // proiezioni
			riempimentoTabella(f.getDtmProiezioni(), "proiezioni", 5);		
			
			// aggiunta degli elementi alla combo sala e film
			 PreparedStatement stmSql = null;
			 ResultSet rs = null;
			 
			 try {
				 stmSql = f.conn.prepareStatement("select CodSala, Nome from sale");
				 rs = stmSql.executeQuery();				 
				 while(rs.next()){
					 f.getCmbSala().addItem(rs.getString("CodSala") + "|" + rs.getString("Nome"));
				 }
				 
				 stmSql = f.conn.prepareStatement("select CodFilm, Titolo from film");
				 rs = stmSql.executeQuery();				 
				 while(rs.next()){
					 f.getCmbFilmProiezione().addItem(rs.getString("CodFilm") + "|" + rs.getString("Titolo"));
				 }
			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	}

}
