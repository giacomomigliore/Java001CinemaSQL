package MainEGrafica;
import gestioneDegliEventi.Gestione;
import gestioneDegliEventi.GestioneCambioTabPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Finestra extends JFrame {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtmFilm = null;
	private DefaultTableModel dtmAttori = null;
	private DefaultTableModel dtmRecita = null;
	private DefaultTableModel dtmSale = null;
	private DefaultTableModel dtmProiezioni = null;
	private DefaultTableModel dtmQuery = null;
	private JButton aggiuntaAttore = null;
	private JButton aggiuntaSala = null;
	private JButton aggiuntaRecita = null;
	private JButton aggiuntaFilm = null;
	private JButton aggiuntaProiezione = null;
	private JTextField titoloFilm = null;
	private JTextField annoProduzione = null;
	private JTextField nazionalitaFilm = null;
	private JTextField regista = null;
	private JTextField genere = null;
	private JTextField nomeAttore = null;
	private JTextField annoNascita = null;
	private JTextField nazionalitaAttore = null;
	private JTextField posti = null;
	private JTextField nomeSala = null;
	private JTextField citta = null;
	private JTextField incasso = null;
	private JTextField dataProiezione = null;
	private JComboBox<String> cmbAttori = null;
	private JComboBox<String> cmbFilmRecita = null;
	private JComboBox<String> cmbFilmProiezione = null;
	private JComboBox<String> cmbSala = null;
	private JComboBox<String> cmbQuery = null;
	private JPanel film = null;
	private JTabbedPane jtp = null;
	private JTable xx= null;
	// la connessione viene creata public qui in modo che opssa essere
	// sfruttata da pi� classi
	public Connection conn = null;
	

	public JTabbedPane getJtp() {
		return jtp;
	}


	public JPanel getFilm() {
		return film;
	}


	/**
	 * Costruttore che crea l'intefaccia
	 */
	public Finestra(String titolo){
		// creo l'oggetto Tabbed Pane e lo aggiungo alla finestra
		jtp = new JTabbedPane();
		this.add(jtp);
		// creo i Panel che mi servono e gli aggiungo a jtp
		film = new JPanel();
		jtp.addTab("Film", film);
		JPanel attori = new JPanel();
		jtp.addTab("Attori", attori);
		JPanel recita = new JPanel();
		jtp.addTab("Recita", recita);
		JPanel sale = new JPanel();
		jtp.addTab("Sale", sale);
		JPanel proiezioni = new JPanel();
		jtp.addTab("Proiezioni", proiezioni);
		JPanel query = new JPanel(new BorderLayout(15, 15));
		jtp.addTab("Queries", query);
		
		
		// Creazione del layout di Film
		// creazione tabella
		String[] colonneDTMFilm = {"Codice Film", "Titolo", "Anno Produzione", "Nazionalit�", "Regista", "Genere"};
		dtmFilm = new DefaultTableModel(colonneDTMFilm,0);
		xx= new JTable(dtmFilm);
		film.setPreferredSize(new Dimension(500, 100));
		film.add(new JScrollPane(xx), BorderLayout.CENTER);
		// gestione inserimento
		JPanel inserimentoFilm = new JPanel(new GridLayout(7, 2, 5, 5));
		film.add(inserimentoFilm, BorderLayout.EAST);
		inserimentoFilm.add(new JLabel("INSERIMENTO FILM"));
		inserimentoFilm.add(new JLabel());
		inserimentoFilm.add(new JLabel("Titolo"));
		titoloFilm = new JTextField();
		inserimentoFilm.add(titoloFilm);
		inserimentoFilm.add(new JLabel("Anno Produzione"));
		annoProduzione = new JTextField();
		inserimentoFilm.add(annoProduzione);
		inserimentoFilm.add(new JLabel("Nazionalit�"));
		nazionalitaFilm= new JTextField();
		inserimentoFilm.add(nazionalitaFilm);
		inserimentoFilm.add(new JLabel("Regista"));
		regista = new JTextField();
		inserimentoFilm.add(regista);
		inserimentoFilm.add(new JLabel("Genere"));
		genere = new JTextField();
		inserimentoFilm.add(genere);
		inserimentoFilm.add(new JLabel(""));
		aggiuntaFilm = new JButton("Aggiungi Film");
		inserimentoFilm.add(aggiuntaFilm);
		
		
		// Creazione del layout di Attori
		// crezione della tabella
		String[] colonneDTMAttori = {"Codice Attore", "Nome", "Anno Nascita", "Nazionalit�"};
		dtmAttori = new DefaultTableModel(colonneDTMAttori, 0);
		attori.add(new JScrollPane(new JTable(dtmAttori)), BorderLayout.CENTER);
		// gestione inserimento
		JPanel inserimentoAttori = new JPanel(new GridLayout(5, 2, 5, 5));
		attori.add(inserimentoAttori, BorderLayout.EAST);
		inserimentoAttori.add(new JLabel("INSERIMENTO ATTORE"));
		inserimentoAttori.add(new JLabel());
		inserimentoAttori.add(new JLabel("Nome"));
		nomeAttore = new JTextField();
		inserimentoAttori.add(nomeAttore);
		inserimentoAttori.add(new JLabel("Anno di nascita"));
		annoNascita = new JTextField();
		inserimentoAttori.add(annoNascita);
		inserimentoAttori.add(new JLabel("Nazionalit�"));
		nazionalitaAttore = new JTextField();
		inserimentoAttori.add(nazionalitaAttore);
		inserimentoAttori.add(new JLabel(""));
		aggiuntaAttore = new JButton("Aggiungi Attore");
		inserimentoAttori.add(aggiuntaAttore);
		inserimentoFilm.add(aggiuntaFilm);
		
		
		// Creazione del layout di Recita
		// crezione della tabella
		String[] colonneDTMRecita = {"Codice Recita", "Attore", "Film"};
		dtmRecita = new DefaultTableModel(colonneDTMRecita, 0);
		recita.add(new JScrollPane(new JTable(dtmRecita)), BorderLayout.CENTER);
		// gestione inserimento
		JPanel inserimentoRecite = new JPanel(new GridLayout(4, 2, 5, 5));
		recita.add(inserimentoRecite, BorderLayout.EAST);
		inserimentoRecite.add(new JLabel("INSERIMENTO RECITA"));
		inserimentoRecite.add(new JLabel());
		inserimentoRecite.add(new JLabel("Attore"));
		cmbAttori = new JComboBox<String>();
		inserimentoRecite.add(cmbAttori);
		inserimentoRecite.add(new JLabel("Film"));
		cmbFilmRecita = new JComboBox<String>();
		inserimentoRecite.add(cmbFilmRecita);
		aggiuntaRecita = new JButton("Aggiungi Recita");
		inserimentoRecite.add(new JLabel());
		inserimentoRecite.add(aggiuntaRecita);
		
		// Creazione del layout delle Sale
		// creazione della tabella
		String[] colonneDTMSale = {"Codice Sala", "Posti", "Nome", "Citt�"};
		dtmSale = new DefaultTableModel(colonneDTMSale, 0);
		sale.add(new JScrollPane(new JTable(dtmSale)), BorderLayout.CENTER);
		// gestione inserimento
		JPanel inserimentoSale = new JPanel(new GridLayout(5, 2, 5, 5));
		sale.add(inserimentoSale, BorderLayout.EAST);
		inserimentoSale.add(new JLabel("INSERIMENTO SALA"));
		inserimentoSale.add(new JLabel());
		inserimentoSale.add(new JLabel("Posti"));
		posti = new JTextField();
		inserimentoSale.add(posti);
		inserimentoSale.add(new JLabel("Nome"));
		nomeSala = new JTextField();
		inserimentoSale.add(nomeSala);
		citta = new JTextField();
		inserimentoSale.add(new JLabel("Citt�"));
		inserimentoSale.add(citta);
		aggiuntaSala = new JButton("Aggiungi Sala");
		inserimentoSale.add(new JLabel());
		inserimentoSale.add(aggiuntaSala);
		
		// Creazione del layout delle proiezioni
		// creazione della tabella
		String[] colonneDTMProiezioni = {"Codice Proiezione", "Film", "Sala", "Incasso", "Data Proiezione"};
		dtmProiezioni = new DefaultTableModel(colonneDTMProiezioni, 0);
		proiezioni.add(new JScrollPane(new JTable(dtmProiezioni)), BorderLayout.CENTER);
		// gestione inserimento
		JPanel inserimentoProiezioni = new JPanel(new GridLayout(6, 2, 5, 5));
		proiezioni.add(inserimentoProiezioni, BorderLayout.EAST);
		inserimentoProiezioni.add(new JLabel("INSERIMENTO PROIEZIONE"));
		inserimentoProiezioni.add(new JLabel());
		cmbFilmProiezione = new JComboBox<String>();
		inserimentoProiezioni.add(new JLabel("Film"));
		inserimentoProiezioni.add(cmbFilmProiezione);
		inserimentoProiezioni.add(new JLabel("Sala"));
		cmbSala = new JComboBox<String>();
		inserimentoProiezioni.add(cmbSala);
		incasso = new JTextField();
		inserimentoProiezioni.add(new JLabel("Incasso"));
		inserimentoProiezioni.add(incasso);
		dataProiezione = new JTextField();
		inserimentoProiezioni.add(new JLabel("Data"));
		inserimentoProiezioni.add(dataProiezione);
		aggiuntaProiezione = new JButton("Aggiungi Proiezione");
		inserimentoProiezioni.add(new JLabel());
		inserimentoProiezioni.add(aggiuntaProiezione);
		
		
		// Crezione del layout delle queries
		// crezione della combo
		DefaultComboBoxModel<String> dcbmQueries = new DefaultComboBoxModel<>();
		cmbQuery = new JComboBox<String>(dcbmQueries);
		query.add(cmbQuery, BorderLayout.NORTH);
		// creazione della tabella
		dtmQuery = new DefaultTableModel();
		query.add(new JScrollPane(new JTable(dtmQuery)), BorderLayout.SOUTH);
		// riempimento della cmb delle query
		dcbmQueries.addElement("1 - Il nome di tutte le sale di Pisa");
		dcbmQueries.addElement("2 - Il titolo dei film di Fellini prodotti dopo il 1960");
		dcbmQueries.addElement("3 - Il titolo dei film di fantascienza giapponesi o francesi prodotti dopo il 1990");
		dcbmQueries.addElement("4 - Il titolo dei film di fantascienza giapponesi dopo il 1990 o francesi");
		dcbmQueries.addElement("5 - Il titolo ed il genere dei film proiettati il giorno di Natale 2004");
		dcbmQueries.addElement("6 - Il titolo ed il genere dei film proiettati a Napoli il giorno di Natale 2004");
		dcbmQueries.addElement("7 - Il nome della sale di Napoli il cui giorno di Natale 2004 �  stato proiettato un film con R. Williams");
		dcbmQueries.addElement("8 - Per ogni film in cui recita un attore francese, il titolo del film ed il nome dell'attore");
		dcbmQueries.addElement("9 - Per ogni film che � stato proiettato a Pisa nel gennaio 2005, il titolo del film ed il nome della sala");
		dcbmQueries.addElement("10 - Il titolo dei film in cui recita M. Mastroieaani oppure S. Loren");
		dcbmQueries.addElement("11 - Il titolo dei film in cui recitano M. Mastroieaani e S. Loren");
		dcbmQueries.addElement("12 - Il titolo dei film dello stesso regista di 'Casablanca'");
		dcbmQueries.addElement("13 - Il numero di sale di Pisa con pi� di 60 posti");
		dcbmQueries.addElement("14 - Il numero totale di posti nelle sale di Pisa");
		dcbmQueries.addElement("15 - Per ogni citt�, il numero dell sale");
		dcbmQueries.addElement("16 - Per ogni citt�, il numero delle sale con pi� di 60 posti");
		dcbmQueries.addElement("17 - Per ogni regista, il numero di film prodotto dopo il 1990");
		dcbmQueries.addElement("18 - Per ogni regista, l'incasso totale di tutte le proiezioni dei suoi film");
		dcbmQueries.addElement("19 - Per ogni film di S. Spielberg, il titolo del film, il numero totale di proiezioni a Pisa e l'incasso totale");
		dcbmQueries.addElement("20 - Per ogni regista e per ogni attore, il numero di film del regista con l'attore");
		dcbmQueries.addElement("21 - Il regista ed il titolo del film in cui recitano meno di 6 attori");
		dcbmQueries.addElement("22 - Per ogni film prodotto dopo il 2000, il codice, il titolo e l'incasso totale di tutte le sue proiezioni");
		dcbmQueries.addElement("23 - Il numero di attori dei film in cui appaiono solo attori nati prima del 1970");
		dcbmQueries.addElement("24 - Per ogni film di fantasicenza, il titolo e l'incasso totale di tutte le sue proiezioni");
		dcbmQueries.addElement("25 - Per ogni film di fantasicenza, il titolo e l'incasso totale di tutte le sue proiezioni successive al 1/1/01");
		dcbmQueries.addElement("26 - Per ogni film di fantasicenza che non � mai stato proiettato prima del 1/1/01 il titolo e l'incaso totale di tutte le sue proiezioni");
		dcbmQueries.addElement("27 - Per ogni sala di Pisa, che nel mese di gennaio 2005 ha incassato pi� di 2000 euro, il nome della sala e l'incasso totale(sempre nel mese di gennaio 2005)");
		dcbmQueries.addElement("28 - Il titolo dei film che non sono mai stati proiettati a Pisa");
		dcbmQueries.addElement("29 - Il titolo dei film che sono stati proiettati solo a Pisa");
		dcbmQueries.addElement("30 - Il titolo dei film dei quali non vi � mai stata una proiezione con incasso superiore a 500 euro");
		dcbmQueries.addElement("31 - Il titolo dei film le cui proiezioni hanno sempre ottenuto un incasso superiore a 500 euro");
		dcbmQueries.addElement("32 - Il nome degli attori italiano che non hanno mai recitato in un film di Fellini");
		dcbmQueries.addElement("33 - Il titolo dei film di Fellini in cui non recitano attori italiani");
		dcbmQueries.addElement("34 - Il titolo dei film senza attori");
		dcbmQueries.addElement("35 - Gli attori che prima del 1960 hanno recitato solo nei film di Fellini");
		dcbmQueries.addElement("36 - Gli attori che hanno recitato in un  fil di Fellini solo prima del 1960");
	
		
		// aggiunta dei listener
		cmbQuery.addActionListener(new Gestione(this));
		aggiuntaAttore.addActionListener(new Gestione(this));
		aggiuntaFilm.addActionListener(new Gestione(this));
		aggiuntaProiezione.addActionListener(new Gestione(this));
		aggiuntaRecita.addActionListener(new Gestione(this));
		aggiuntaSala.addActionListener(new Gestione(this));
		jtp.addChangeListener(new GestioneCambioTabPage(this));
		
		
		this.pack();
	}


	public JTable getXx() {
		return xx;
	}


	public void setXx(JTable xx) {
		this.xx = xx;
	}


	public JTextField getTitoloFilm() {
		return titoloFilm;
	}


	public void setTitoloFilm(JTextField titoloFilm) {
		this.titoloFilm = titoloFilm;
	}


	public JTextField getAnnoProduzione() {
		return annoProduzione;
	}


	public void setAnnoProduzione(JTextField annoProduzione) {
		this.annoProduzione = annoProduzione;
	}


	public JTextField getNazionalitaFilm() {
		return nazionalitaFilm;
	}


	public void setNazionalitaFilm(JTextField nazionalitaFilm) {
		this.nazionalitaFilm = nazionalitaFilm;
	}


	public JTextField getRegista() {
		return regista;
	}


	public void setRegista(JTextField regista) {
		this.regista = regista;
	}


	public JTextField getGenere() {
		return genere;
	}


	public void setGenere(JTextField genere) {
		this.genere = genere;
	}


	public JTextField getNomeAttore() {
		return nomeAttore;
	}


	public void setNomeAttore(JTextField nomeAttore) {
		this.nomeAttore = nomeAttore;
	}


	public JTextField getAnnoNascita() {
		return annoNascita;
	}


	public void setAnnoNascita(JTextField annoNascita) {
		this.annoNascita = annoNascita;
	}


	public JTextField getNazionalitaAttore() {
		return nazionalitaAttore;
	}


	public void setNazionalitaAttore(JTextField nazionalitaAttore) {
		this.nazionalitaAttore = nazionalitaAttore;
	}


	public JTextField getPosti() {
		return posti;
	}


	public void setPosti(JTextField posti) {
		this.posti = posti;
	}


	public JTextField getNomeSala() {
		return nomeSala;
	}


	public void setNomeSala(JTextField nomeSala) {
		this.nomeSala = nomeSala;
	}


	public JTextField getCitta() {
		return citta;
	}


	public void setCitta(JTextField citta) {
		this.citta = citta;
	}


	public JTextField getIncasso() {
		return incasso;
	}


	public void setIncasso(JTextField incasso) {
		this.incasso = incasso;
	}


	public JTextField getDataProiezione() {
		return dataProiezione;
	}


	public void setDataProiezione(JTextField dataProiezione) {
		this.dataProiezione = dataProiezione;
	}


	public DefaultTableModel getDtmFilm() {
		return dtmFilm;
	}


	public DefaultTableModel getDtmAttori() {
		return dtmAttori;
	}


	public DefaultTableModel getDtmRecita() {
		return dtmRecita;
	}


	public DefaultTableModel getDtmSale() {
		return dtmSale;
	}


	public DefaultTableModel getDtmProiezioni() {
		return dtmProiezioni;
	}


	public DefaultTableModel getDtmQuery() {
		return dtmQuery;
	}


	public JButton getAggiuntaAttore() {
		return aggiuntaAttore;
	}


	public JButton getAggiuntaSala() {
		return aggiuntaSala;
	}


	public JButton getAggiuntaRecita() {
		return aggiuntaRecita;
	}


	public JButton getAggiuntaFilm() {
		return aggiuntaFilm;
	}


	public JButton getAggiuntaProiezione() {
		return aggiuntaProiezione;
	}


	public JComboBox<String> getCmbAttori() {
		return cmbAttori;
	}


	public JComboBox<String> getCmbFilmRecita() {
		return cmbFilmRecita;
	}


	public JComboBox<String> getCmbFilmProiezione() {
		return cmbFilmProiezione;
	}


	public JComboBox<String> getCmbSala() {
		return cmbSala;
	}


	public JComboBox<String> getCmbQuery() {
		return cmbQuery;
	}

}
