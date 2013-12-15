package gestioneDegliEventi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import MainEGrafica.Finestra;

// show index from mostra chiavi primarie indici ecc

public class Gestione extends WindowAdapter implements ActionListener {
	
	private Finestra f = null;
	
	/**
	 * Costruttore al quale viene passata una finestra
	 * @param fin
	 */
	public Gestione(Finestra fin){
		f = fin;
	}
	
	/**
	 * Funzione che esegue una qualsiasi query a lei passata come parametro
	 * @param istruzione sql da eseguire
	 * @param colonne da inserire nella tabella che conterrà i risultati della query
	 */
	private void query(String istruzione, Vector<String> colonne){
		// setto alla tabella le colonne che preferisco
		f.getDtmQuery().setColumnIdentifiers(colonne);
		
		// istruzioni per la query
		PreparedStatement stmSql = null;
		ResultSet rs = null;
		int x = 1;  // indice usato nei cicli while
		
		try {
			stmSql = f.conn.prepareStatement(istruzione);
			rs = stmSql.executeQuery();

			// riempimento della tabella
			while(rs.next()){
				Vector<String> riga = new Vector<String>();
				x = 1;
				
				// leggo ogni valore presente in rs e lo aggiungo alla riga
				while(x <= colonne.size()){
					riga.add(rs.getString(x));
					x++;
				}
				f.getDtmQuery().addRow(riga);  // aggiungo la riga al dtm
			}
		} catch (SQLException e) {
			System.out.println("Errore nella richiesta della query");
		}
	}
	
	/**
	 * Returns istruzione perchè il tipo String non è passato per referenza ma per valore.
	 * @param colonne vettore in cui si inserisce ogni colonna
	 * @return
	 */
	private String switchScelte(Vector<String> colonne){
		 // un caso per ogni riga selezionata
		 // siccome l'indice parte da zero, il primo case è la query 1
		 String istruzione = null;
		 switch(f.getCmbQuery().getSelectedIndex()){
		 case 0:  // 1 - Il nome di tutte le sale di Pisa
			 colonne.add("Codice Sala");
			 colonne.add("Nome");
			 istruzione = "select CodSala, Nome"
			 		+ " from sale "
			 		+ "where citta = 'Pisa'";
			 break;
		 case 1:  // 2 - Il titolo dei film di Fellini prodotti dopo il 1960
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select CodFilm, Titolo "
			 		+ "from film "
			 		+ "where regista = 'F. Fellini' "
			 		+ "and annoproduzione > 1960";
			 break;
		 case 2:  // 3 - Il titolo dei film di fantascienza giapponesi o francesi prodotti dopo il 1990
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select CodFilm, Titolo "
			 		+ "from film "
			 		+ "where genere = 'Fantascienza' "
			 		+ "and annoproduzione > 1990 "
			 		+ "and (nazionalita = 'Francese' or nazionalita = 'Giapponese')";
			 break;
		 case 3:  // 4 - Il titolo dei film di fantascienza giapponesi dopo il 1990 o francesi
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select CodFilm, Titolo "
			 		+ "from film "
			 		+ "where (genere = 'Fantascienza' and annoproduzione > 1990 and nazionalita = 'Giapponese') "
			 		+ "or nazionalita = 'Francese'";
			 break;
		 case 4:  // 5 - Il titolo ed il genere dei film proiettati il giorno di Natale 2004
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Genere");
			 istruzione = "select f.CodFilm, Titolo, Genere "
			 		+ "from film f join proiezioni p on f.CodFilm = p.CodFilm "
			 		+ "where dataProiezione = '2004-12-25'";
			 break;
		 case 5:  // 6 - Il titolo ed il genere dei film proiettati a Napoli il giorno di Natale 2004
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Genere");
			 istruzione = "select f.CodFilm, titolo, genere "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "join sale s on s.codsala=p.codsala "
			 		+ "where citta='Napoli' "
			 		+ "and dataproiezione='2004-12-25'";
			 break;
		 case 6:  // 7 - Il nome della sale di Napoli il cui giorno di Natale 2004 �  stato proiettato un film con R. Williams
			 colonne.add("Sala");
			 istruzione = "select s.Nome "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "join sale s on s.codsala=p.codsala "
			 		+ "join recita r on r.codfilm=f.codfilm "
			 		+ "join attori a on a.codattore=r.codattore "
			 		+ "where citta='Napoli' "
			 		+ "and dataproiezione='2004-12-25' "
			 		+ "and a.Nome='R. Williams'";
			 break;
		 case 7:  // 8 - Per ogni film in cui recita un attore francese, il titolo del film ed il nome dell'attore
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Attore");
			 istruzione = "select f.CodFilm, titolo, a.nome "
			 		+ "from film f join recita r on r.codfilm=f.codfilm "
			 		+ "join attori a on a.codattore=r.codattore "
			 		+ "where a.nazionalita='Francese'";
			 break;
		 case 8:  // 9 - Per ogni film che � stato proiettato a Pisa nel gennaio 2005, il titolo del film ed il nome della sala
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Sala");
			 istruzione = "select f.CodFilm, titolo, s.nome "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "join sale s on s.codsala=p.codsala "
			 		+ "where citta='Pisa' "
			 		+ "and month(dataproiezione)=1 "
			 		+ "and year(dataproiezione)=2005";
			 break;
		 case 9:  // 10 - Il titolo dei film in cui recita M. Mastroieaani oppure S. Loren
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select f.CodFilm, titolo "
			 		+ "from film f join recita r on f.codfilm=r.codfilm "
			 		+ "join attori a on a.codattore=r.codattore "
			 		+ "where a.nome='M. Mastroianni' "
			 		+ "or a.nome='S. Loren' "
			 		+ "group by titolo, CodFilm";
			 break;
		 case 10:  // 11 - Il titolo dei film in cui recitano M. Mastroieaani e S. Loren
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select CodFilm, titolo "
			 		+ "from film "
			 		+ "where codfilm in("
			 		+ "select codfilm "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "where a.nome= 'S. Loren') "
			 		+ "and codfilm in("
			 		+ "select codfilm "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "where a.nome= 'M. Mastroianni')";
			 break;
		 case 11:  // 12 - Il titolo dei film dello stesso regista di 'Casablanca
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select CodFilm, titolo "
			 		+ "from film "
			 		+ "where regista in ("
			 		+ "select regista "
			 		+ "from film "
			 		+ "where titolo='Casablanca')";
			 break;
		 case 12:  // 13 - Il numero di sale di Pisa con pi� di 60 posti
			 colonne.add("Sala");
			 istruzione = "select count(*) "
			 		+ "from sale "
			 		+ "where citta='Pisa' "
			 		+ "and posti>60";
			 break;
		 case 13:  // 14 - Il numero totale di posti nelle sale di Pisa
			 colonne.add("Posti");
			 istruzione = "select sum(posti) "
			 		+ "from sale "
			 		+ "where citta='Pisa'";
			 break;
		 case 14:  // 15 - Per ogni citt�, il numero dell sale
			 colonne.add("Città");
			 colonne.add("Numero Sale");
			 istruzione = "select citta, count(*) "
			 		+ "from sale "
			 		+ "group by(citta)";
			 break;
		 case 15:  // 16 - Per ogni citt�, il numero delle sale con pi� di 60 posti
			 colonne.add("Città");
			 colonne.add("Numero Sale con più di 60 posti");
			 istruzione = "select citta, count(*) "
			 		+ "from sale "
			 		+ "where posti>60 "
			 		+ "group by(citta)";
			 break;
		 case 16:  // 17 - Per ogni regista, il numero di film prodotto dopo il 1990
			 colonne.add("Regista");
			 colonne.add("Film diretti");
			 istruzione = "select regista, count(*) "
			 		+ "from film "
			 		+ "where annoproduzione>1990 "
			 		+ "group by regista";
			 break;
		 case 17:  // 18 - Per ogni regista, l'incasso totale di tutte le proiezioni dei suoi film
			 colonne.add("Regista");
			 colonne.add("Incasso totale");
			 istruzione = "select regista, sum(incasso) "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "group by regista";
			 break;
		 case 18:  // 19 - Per ogni film di S. Spielberg, il titolo del film, il numero totale di proiezioni a Pisa e l'incasso totale")
			 colonne.add("Codice Film");
			 colonne.add("Numero proiezioni a Pisa");
			 colonne.add("Incasso totale");
			 istruzione = "select f.CodFilm, titolo, count(*), sum(incasso) "
			 		+ "from film f join proiezioni p on f.codfilm= p.codfilm "
			 		+ "where regista= 'R. Spielberg' "
			 		+ "group by titolo";
			 break;
		 case 19:  // 20 - Per ogni regista e per ogni attore, il numero di film del regista con l'attore
			 colonne.add("Regista");
			 colonne.add("Attore");
			 colonne.add("Numero Film");
			 istruzione = "select regista, nome, count(*) "
			 		+ "from film f join recita r on f.codfilm=r.codfilm "
			 		+ "join attori a on a.codattore=r.codattore "
			 		+ "group by regista, nome";
			 break;
		 case 20:  // 21 - Il regista ed il titolo del film in cui recitano meno di 6 attori
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Regista");
			 colonne.add("Numero Attori");
			 istruzione = "select f.CodFilm, titolo, regista, count(*) "
			 		+ "from film f join recita r on f.CodFilm = r.CodFilm "
			 		+ "join attori a on a.CodAttore = r.CodAttore "
			 		+ "group by f.CodFilm, titolo, regista "
			 		+ "having count(*) < 6";
			 break;
		 case 21:
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Incasso totale");	
			 istruzione = "select f.codfilm, titolo, sum(incasso) "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "where annoproduzione > 2000 "
			 		+ "group by f.codfilm, titolo";
			 break;
		 case 22:  // 23 - Il numero di attori dei film in cui appaiono solo attori nati prima del 1970
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Numero Attori");			 
			 istruzione = "select f.codfilm, titolo, count(*) "
			 		+ "from film f join recita r on f.codfilm = r.codfilm "
			 		+ "join attori a on a.codattore=r.codattore "
			 		+ "where f.codfilm not in ("
			 		+ "select f.codfilm "
			 		+ "from film f join recita r on f.codfilm = r.codfilm "
			 		+ "join attori a on a.codattore=r.codattore "
			 		+ "where a.annonascita>1970 "
			 		+ "group by f.codfilm)"
			 		+ "group by f.codfilm, titolo";
			 break;
		 case 23:  // 24 - Per ogni film di fantasicenza, il titolo e l'incasso totale di tutte le sue proiezioni
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Incasso totale");	
			 istruzione = "select f.codfilm, titolo, sum(incasso) "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "where genere = 'Fantascienza' "
			 		+ "group by f.codfilm, titolo";
			 break;
		 case 24:  // 25 - Per ogni film di fantasicenza, il titolo e l'incasso totale di tutte le sue proiezioni successive al 1/1/01
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Incasso totale");	
			 istruzione = "select f.codfilm, titolo, sum(incasso) "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "where genere = 'Fantascienza' "
			 		+ "and Year(dataproiezione)>2000 "
			 		+ "group by f.codfilm, titolo";
			 break;
		 case 25:  // 26 - Per ogni film di fantasicenza che non � mai stato proiettato prima del 1/1/01 il titolo e l'incaso totale di tutte le sue proiezioni
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 colonne.add("Incasso totale");	
			 istruzione = "select f.codfilm, titolo, sum(incasso) "
			 		+ "from film f join proiezioni p on f.codfilm=p.codfilm "
			 		+ "where f.codfilm not in("
			 		+ "select codfilm "
			 		+ "from proiezioni "
			 		+ "where Year(dataproiezione) < 2001 "
			 		+ "group by codfilm) "
			 		+ "and genere = 'Fantascienza' "
			 		+ "group by f.codfilm, titolo";
			 break;
		 case 26:  // 27 - Per ogni sala di Pisa, che nel mese di gennaio 2005 ha incassato pi� di 2000 euro, il nome della sala e l'incasso totale(sempre nel mese di gennaio 2005
			 colonne.add("Codice Sala");
			 colonne.add("Nome");
			 colonne.add("Incasso totale");	
			 istruzione = "select s.codsala, nome, sum(incasso) "
			 		+ "from sale s join proiezioni p on s.codsala = p.codsala  "
			 		+ "where citta = 'Pisa' and month(dataproiezione) = 1 "
			 		+ "and year(dataproiezione) = 2005 "
			 		+ "group by s.codsala, nome "
			 		+ "having sum(incasso) > 20000";
			 break;
		 case 27:  // 28 - Il titolo dei film che non sono mai stati proiettati a Pisa
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select codfilm, titolo "
			 		+ "from film "
			 		+ "where codfilm not in("
			 		+ "select codfilm "
			 		+ "from proiezioni p join sale s on s.codsala = p.codsala "
			 		+ "where citta = 'Pisa' "
			 		+ "group by codfilm)"
			 		+ "group by codfilm, titolo";
			 break;
		 case 28:  // 29 - Il titolo dei film che sono stati proiettati solo a Pisa
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select f.codfilm, titolo "
			 		+ "from  proiezioni p join sale s on s.codsala = p.codsala "
			 		+ "join film f on f.codfilm = p.codfilm "
			 		+ "where citta = 'Pisa' "
			 		+ "and f.codfilm not in("
			 		+ "select p.codfilm "
			 		+ "from proiezioni p join sale s on s.codsala = p.codsala "
			 		+ "where citta <> 'Pisa'"
			 		+ " group by p.codfilm) "
			 		+ "group by f.codfilm, titolo";
			 break;
		 case 29:  // 30 - Il titolo dei film dei quali non vi � mai stata una proiezione con incasso superiore a 500 euro
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select codfilm, titolo "
			 		+ "from film "
			 		+ "where codfilm not in "
			 		+ "(select codfilm "
			 		+ "from proiezioni "
			 		+ "where incasso > 500 "
			 		+ "group by codfilm)";
			 break;
		 case 30:  // 31 - Il titolo dei film le cui proiezioni hanno sempre ottenuto un incasso superiore a 500 euro
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select codfilm, titolo "
			 		+ "from film "
			 		+ "where codfilm not in("
			 		+ "select codfilm "
			 		+ "from proiezioni "
			 		+ "where incasso < 500 "
			 		+ "group by codfilm)";
			 break;
		 case 31:  // 32 - Il nome degli attori italiano che non hanno mai recitato in un film di Fellini
			 colonne.add("Codice Attore");
			 colonne.add("Nome");
			 istruzione = "select codattore, nome "
			 		+ "from attori "
			 		+ "where nazionalita = 'Italiano' "
			 		+ "and codattore not in("
			 		+ "select a.codattore "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "join film f on f.codfilm = r.codfilm "
			 		+ "where regista = 'F. Fellini' "
			 		+ "group by a.codattore)";
			 break;
		 case 32:  // 33 - Il titolo dei film di Fellini in cui non recitano attori italiani
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select codfilm, titolo "
			 		+ "from film "
			 		+ "where regista = 'F. Fellini' "
			 		+ "and codfilm not in("
			 		+ "select codfilm "
			 		+ "from recita r join attori a on r.codattore=a.codattore "
			 		+ "where nazionalita = 'Italiano' "
			 		+ "group by codfilm)";
			 break;
		 case 33:  // 34 - Il titolo dei film senza attori
			 colonne.add("Codice Film");
			 colonne.add("Titolo");
			 istruzione = "select codfilm, titolo"
			 		+ "from film "
			 		+ "where codfilm not in("
			 		+ "select codfilm "
			 		+ "from recita "
			 		+ "group by codfilm)";
			 break;
		 case 34:  // 35 - Gli attori che prima del 1960 hanno recitato solo nei film di Fellini
			 colonne.add("Codice Attore");
			 colonne.add("Nome");
			 istruzione = "select a.codattore, nome "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "join film f on f.codfilm=r.codfilm "
			 		+ "where annoproduzione < 1960 "
			 		+ "and regista ='F. Fellini' "
			 		+ "and a.codattore not in ("
			 		+ "select a.codattore "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "join film f on f.codfilm=r.codfilm "
			 		+ "where annoproduzione < 1960 "
			 		+ "and regista <> 'F. Fellini' "
			 		+ "group by a.codattore)";
			 break;
		 case 35:  // 36 - Gli attori che hanno recitato in un  fil di Fellini solo prima del 1960
			 colonne.add("Codice Attore");
			 colonne.add("Nome");
			 istruzione = "select a.codattore, nome "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "join film f on f.codfilm=r.codfilm "
			 		+ "where annoproduzione < 1960 "
			 		+ "and regista ='F. Fellini' "
			 		+ "and a.codattore not in ("
			 		+ "select a.codattore "
			 		+ "from attori a join recita r on a.codattore=r.codattore "
			 		+ "join film f on f.codfilm=r.codfilm "
			 		+ "where annoproduzione > 1960 "
			 		+ "and regista = 'F. Fellini' "
			 		+ "group by a.codattore)";
			 break;
		 }
		 return istruzione;
	}

	/**
	 * Metodo che viene richiamato ogni volta che viene premuto un pulsante
	 * o selezionato qualcosa dalla combobox delle query
	 */
	 @Override
	public void actionPerformed(ActionEvent e) {
		 Vector<String> riga = null;  // variabile utilizzata per agguingere righe alle tabella
		
		 // Usato nel cambio delle query. Risolve una query e la stampa
		 if(e.getSource() == f.getCmbQuery()){
			 // pulizia della tabella
			 while( f.getDtmQuery().getRowCount() > 0 ) f.getDtmQuery().removeRow(0);
			 
			 // query
			 Vector<String> colonne = new Vector<>();
			 String istruzione = switchScelte(colonne);
			 query(istruzione, colonne);
		 }
		 
		 // aggiunta degli attori
		 else if(e.getSource() == f.getAggiuntaAttore()){
			 if(f.getNomeAttore().getText().equals("") || f.getNazionalitaAttore().getText().equals("") || f.getAnnoNascita().getText().equals("")){
				 JOptionPane.showMessageDialog(null, "Completare tutti i campi");
			 }else{
				 try{
					 // controllo che sia numerico
					 Integer.parseInt(f.getAnnoNascita().getText());
					 
					 // creazione dell'istruzione e aggiunta dei parametri
					 PreparedStatement stmSql = null; 
					 stmSql = f.conn.prepareStatement("insert into attori (Nome, AnnoNascita, Nazionalita) values (?, ?, ?)");
					 stmSql.setString(1, f.getNomeAttore().getText());
					 stmSql.setInt(2, Integer.parseInt(f.getAnnoNascita().getText()));
					 stmSql.setString(3, f.getNazionalitaAttore().getText());
					 stmSql.executeUpdate();
					 
					 // aggiunta della riga alla tabella
					 riga = new Vector<String>();
					 riga.add("Unknown");
					 riga.add(f.getNomeAttore().getText());
					 riga.add(f.getAnnoNascita().getText());
					 riga.add(f.getNazionalitaAttore().getText());
					 f.getDtmAttori().addRow(riga);
					 
					 // pulizia dei textField
					 f.getNomeAttore().setText("");
					 f.getAnnoNascita().setText("");
					 f.getNazionalitaAttore().setText(""); 
				 }catch(NumberFormatException e1){
					 JOptionPane.showMessageDialog(null, "Inserire un valore numerico nel campo 'Anno di Nascita'");
				 }catch (SQLException e2) {

				}
			 }
		 }
		 
		 // aggiunta dei film
		 else if(e.getSource() == f.getAggiuntaFilm()){  
			 if(f.getTitoloFilm().getText().equals("") || f.getAnnoProduzione().getText().equals("") || f.getNazionalitaFilm().getText().equals("") || f.getRegista().getText().equals("") || f.getGenere().getText().equals("")){
				 JOptionPane.showMessageDialog(null, "Completare tutti i campi");
			 }else{
				 try {
					 // controllo che sia numerico
					 Integer.parseInt(f.getAnnoProduzione().getText());

					 // creazione dell'istruzione e aggiunta dei parametri
					 PreparedStatement stmSql = null;
					 stmSql = f.conn.prepareStatement("insert into film (titolo, annoproduzione, nazionalita, regista, genere) values (?, ?, ?, ?, ?)");
					 stmSql.setString(1, f.getTitoloFilm().getText());
					 stmSql.setInt(2, Integer.parseInt(f.getAnnoProduzione().getText()));
					 stmSql.setString(3, f.getNazionalitaFilm().getText());
					 stmSql.setString(4, f.getRegista().getText());
					 stmSql.setString(5, f.getGenere().getText());					 						
					 stmSql.executeUpdate();
					 
					 // aggiunta della riga alla tabella
					 riga = new Vector<String>();
					 riga.add("Unknown");
					 riga.add(f.getTitoloFilm().getText());
					 riga.add(f.getAnnoProduzione().getText());
					 riga.add(f.getNazionalitaFilm().getText());
					 riga.add(f.getRegista().getText());
					 riga.add(f.getGenere().getText());
					 f.getDtmFilm().addRow(riga);
					 
					 // pulizia dei textField
					 f.getTitoloFilm().setText("");
					 f.getAnnoProduzione().setText("");
					 f.getNazionalitaFilm().setText("");
					 f.getRegista().setText("");
					 f.getGenere().setText("");
					 } catch (SQLException e1) {
						 System.out.println("Errore inserimento SQL");
					 } catch (NumberFormatException e2) {
						 JOptionPane.showMessageDialog(null, "Inserire valori numerici in Anno Produzione!");
					}
			 }
		 }
		 
		 // aggiunta proiezioni
		 else if(e.getSource() == f.getAggiuntaProiezione()){
			 try {
				 // creazione dell'istruzione e aggiunta dei parametri
				 PreparedStatement stmSql = null; 
				 stmSql = f.conn.prepareStatement("insert into proiezioni (CodFilm, CodSala, Incasso, DataProiezione) values (?, ?, ?, ?)");
				 stmSql.setInt(1, Integer.parseInt(f.getCmbFilmProiezione().getSelectedItem().toString().split(Pattern.quote("|"))[0]));
				 stmSql.setInt(2, Integer.parseInt(f.getCmbSala().getSelectedItem().toString().split(Pattern.quote("|"))[0]));
				 stmSql.setInt(3, Integer.parseInt(f.getIncasso().getText().toString()));
				 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				 stmSql.setDate(4, new java.sql.Date(dateFormat.parse(f.getDataProiezione().getText()).getTime()));
				 stmSql.executeUpdate();
				 
				 // aggiunta della riga alla tabella
				 riga = new Vector<String>();
				 riga.add("Unknown");
				 riga.add(f.getCmbFilmProiezione().getSelectedItem().toString().split(Pattern.quote("|"))[1]);
				 riga.add(f.getCmbSala().getSelectedItem().toString().split(Pattern.quote("|"))[1]);
				 riga.add(f.getIncasso().getText().toString());
				 riga.add(f.getDataProiezione().getText().toString());
				 f.getDtmProiezioni().addRow(riga);
				 
				 //pulizia delle text
				 f.getIncasso().setText("");
				 f.getDataProiezione().setText("");
			 } catch (SQLException e1) {
				 e1.printStackTrace();
				 System.out.println("Errore inserimento SQL");
			 } catch(NumberFormatException e2){
				 JOptionPane.showMessageDialog(null, "Inserire valori numerici in 'Incasso'!");
			 } catch(ParseException e3){
				 JOptionPane.showMessageDialog(null, "Inserire una data nel formato gg/mm/aaaa!");
			 }
		 }
		 
		 // aggiunta delle recite
		 else if(e.getSource() == f.getAggiuntaRecita()){
			 try {
				 // creazione dell'istruzione e aggiunta dei parametri
				 PreparedStatement stmSql = null;
				 stmSql = f.conn.prepareStatement("insert into recita (CodAttore, CodFilm) values (?, ?)");
				 stmSql.setInt(1, Integer.parseInt(f.getCmbAttori().getSelectedItem().toString().split(Pattern.quote("|"))[0]));
				 stmSql.setInt(2, Integer.parseInt(f.getCmbFilmRecita().getSelectedItem().toString().split(Pattern.quote("|"))[0]));
				 stmSql.executeUpdate();
				 
				 // aggiunta della riga alla tabella
				 riga = new Vector<String>();
				 riga.add("Unknown");
				 riga.add(f.getCmbAttori().getSelectedItem().toString().split(Pattern.quote("|"))[1]);
				 riga.add(f.getCmbFilmRecita().getSelectedItem().toString().split(Pattern.quote("|"))[1]);
				 f.getDtmRecita().addRow(riga);
				 } catch (SQLException e1) {
					 e1.printStackTrace();
					 System.out.println("Errore inserimento SQL");
				 }
		 
		 }
		 
		 // aggiunta sale
		 else if(e.getSource() == f.getAggiuntaSala()){
			 if(f.getNomeSala().getText().equals("") || f.getPosti().getText().equals("") || f.getCitta().getText().equals("")){
				 JOptionPane.showMessageDialog(null, "Completare tutti i campi");
			 }else{
				 try {
					 // controllo che sia numerico
					 Integer.parseInt(f.getPosti().getText());

					 // creazione dell'istruzione e aggiunta dei parametri
					 PreparedStatement stmSql = null;
					 stmSql = f.conn.prepareStatement("insert into sale (Nome, Posti, Citta) values (?, ?, ?)");
					 stmSql.setString(1, f.getNomeSala().getText());
					 stmSql.setInt(2, Integer.parseInt(f.getPosti().getText()));
					 stmSql.setString(3, f.getCitta().getText());		 						
					 stmSql.executeUpdate();
					 
					 // aggiunta della riga alla tabella
					 riga = new Vector<String>();
					 riga.add("Unknown");
					 riga.add(f.getPosti().getText());
					 riga.add(f.getNomeSala().getText());
					 riga.add(f.getCitta().getText());
					 f.getDtmSale().addRow(riga);
					 
					 // pulizia dei textField
					 f.getPosti().setText("");
					 f.getNomeSala().setText("");
					 f.getCitta().setText("");
					 } catch (SQLException e1) {
						 System.out.println("Errore inserimento SQL");
					 } catch (NumberFormatException e2) {
						 JOptionPane.showMessageDialog(null, "Inserire valori numerici in 'Posti'!");
					}
			 }
		 
		 }			 
	}

	 /**
	  * Metodo richiamato quando si apre la finestra, apre la connessione
	  */
	@Override
	public void windowOpened(WindowEvent arg0) {
		super.windowOpened(arg0);
		String stringaConn = null;
		String nomeClasse = null;
		// stringa per il database su linux
		stringaConn = "jdbc:mysql://localhost/cinemapreverificasql?user=root&password=root";
		// stringa per il database su windows
		//stringaConn = "jdbc:mysql://localhost/cinemapreverificasql?user=root";
		nomeClasse = "com.mysql.jdbc.Driver";
		// nel caso non trovi la classe
		try {
			Class.forName(nomeClasse);
			System.out.println("forName eseguito");
		} catch (ClassNotFoundException e) {
			System.out.println("Classe nomeClasse - Driver non trovata");
		}
		// nel caso in cui la connessione non avvenga
		try {
			f.conn = DriverManager.getConnection(stringaConn);
			System.out.println("Aperta la connessione");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Stringa di connessione errata");
		}
		
		
		
		// riempo la tabella film che � la prima tabella visualizzaa
		PreparedStatement stmSql = null;
		ResultSet rs = null;
		int x = 1;  // usato per riempire le righe
		
		try {
			stmSql = f.conn.prepareStatement("select * from film");
			rs = stmSql.executeQuery();
			
			while(rs.next()){  // uso lo stesso ciclo usato nella classe GestinoCambioTabPage
				Vector<String> riga = new Vector<String>();
				x = 1;
				while(x <= 6){
					riga.add(rs.getString(x));
					x++;
				}
				f.getDtmFilm().addRow(riga);
			}
		} catch (SQLException e) {
			System.out.println("Errore nella richiesta della query");
		}
	}

	/**
	 * Metodo richiamato quando si chiude una finestra, chiude la connessione
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowClosing(arg0);
		try {
			f.conn.close();
			System.out.println("Chiusa la connessione");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("Errore, nella chiusura della finestra");
		}
	}

}