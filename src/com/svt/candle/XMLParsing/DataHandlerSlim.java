package com.svt.candle.XMLParsing;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import Database.DatabaseManager;
import android.util.Log;

/**
 *  trieda na parsovanie xml a ukladanie do databazy xperia8 1:30, ignoruje tretinu atributov, ktore nemaju pouzitie
 *  Okresany o atributy: ucitel - iniciala,katedra, oddelenie; miestnost - kapacita, typ, aiskod; predmet - kod, kratkykod,
 *  kredity, rozsah; hodiny - trvanie, oldid, zviazanehodiny, zviazaneoldid
 */

public class DataHandlerSlim extends DefaultHandler {
	// na vkladanie udajov do databazy
	private DatabaseManager dbManager = null;
	// zakladne
	private Boolean bUcitelia = false;
	private Boolean bMiestnosti = false;
	private Boolean bPredmety = false;
	private Boolean bHodiny = false;

	private HodinaData hodinaData = null;
	private PredmetyData predmetyData = null;
	private UciteliaData uciteliaData = null;

	String chars = null;

	public DataHandlerSlim(DatabaseManager dbManager) {
		this.dbManager = dbManager;
		predmetyData = new PredmetyData();
		hodinaData = new HodinaData();
		uciteliaData = new UciteliaData();
	}

	@Override
	public void startDocument() throws SAXException {
		Log.d("ParserXMl", "Dostal som sa do handlera1");
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, org.xml.sax.Attributes atts) throws SAXException {

		if (bHodiny) {
			if (localName.charAt(0) == 'h') {
				hodinaData.ID = atts.getValue("id");
			}
		} else if (bUcitelia) {
			if (localName.charAt(0) == 'u') {
				uciteliaData.ID = atts.getValue("id");
			}
		} else if (bPredmety) {
			if (localName.charAt(0) == 'p') {
				predmetyData.ID = atts.getValue("id");
			}
		} else if (bMiestnosti) { // setrenie casu
		} else if (localName.equals("typ")) {
			dbManager.insertTypHodin(atts.getValue("id"),
					atts.getValue("popis"));
		} else if (localName.equals("typmiestnosti")) {
			dbManager.insertTypMiestnosti(atts.getValue("id"),
					atts.getValue("popis"));
		} else if (localName.equals("hodiny")) {
			Log.d("parsovanie", "hodiny");
			bHodiny = true;
		} else if (localName.equals("ucitelia")) {
			bUcitelia = true;
			Log.d("parsovanie", "ucitelia");
		} else if (localName.equals("predmety")) {
			bPredmety = true;
			Log.d("parsovanie", "predmety");
		} else if (localName.equals("miestnosti")) {
			bMiestnosti = true;
			Log.d("parsovanie", "miestnosti");
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {

		if (bHodiny) {
			if (localName.charAt(0) == 'd')
				hodinaData.DEN = chars;
			else if (localName.charAt(0) == 'z') {
				if (localName.charAt(1) == 'a')
					hodinaData.ZACIATOK = chars;
			} else if (localName.charAt(0) == 'k')
				if (localName.charAt(1) == 'o')
					hodinaData.KONIEC = chars;
				else
					hodinaData.KRUZKY = chars;
			else if (localName.charAt(0) == 'm')
				hodinaData.MIESTNOST = chars;
			else if (localName.charAt(0) == 't') {
				if (localName.charAt(1) == 'y')
					hodinaData.TYP = chars;

			} else if (localName.charAt(0) == 'p')
				if (localName.charAt(1) == 'o')
					hodinaData.POZNAMKA = chars;
				else
					hodinaData.PREDMET = chars;
			else if (localName.charAt(0) == 'u')
				hodinaData.UCITELIA = chars;
			else if (localName.charAt(0) == 'o') {
			} else if (localName.charAt(5) == 'y')
				bHodiny = false;
			else {
				dbManager.insertHodina(hodinaData.ID, hodinaData.DEN,
						hodinaData.ZACIATOK, hodinaData.KONIEC,
						hodinaData.MIESTNOST, hodinaData.TRVANIE,
						hodinaData.PREDMET, hodinaData.UCITELIA,
						hodinaData.KRUZKY, hodinaData.TYP, hodinaData.OLDID,
						hodinaData.POZNAMKA);
				// Log.d("hodina", hodinaData.ID+ hodinaData.DEN+
				// hodinaData.ZACIATOK+ hodinaData.KONIEC+
				// hodinaData.MIESTNOST+ hodinaData.TRVANIE+
				// hodinaData.PREDMET+ hodinaData.UCITELIA+
				// hodinaData.KRUZKY+ hodinaData.TYP+ hodinaData.OLDID+
				// hodinaData.POZNAMKA );
			}

		} else if (bPredmety) {

			if (localName.charAt(0) == 'n')
				predmetyData.NAZOV = chars;
			else if (localName.charAt(0) == 'p') {
				if (localName.length() == 8)
					bPredmety = false;
				else {
					dbManager.insertPredmety(predmetyData.ID,
							predmetyData.NAZOV, null, null, null, null);
					// Log.d("predmety", predmetyData.ID + predmetyData.NAZOV +
					// predmetyData.KOD + predmetyData.KRATKYKOD +
					// predmetyData.KREDITY + predmetyData.ROZSAH);
				}

			}

		} else if (bUcitelia) {

			if (localName.charAt(0) == 'm')
				uciteliaData.MENO = chars;

			else if (localName.charAt(0) == 'p')
				uciteliaData.PRIEZVISKO = chars;

			else if (localName.length() == 8)
				bUcitelia = false;
			else {
				dbManager.insertUcitel(uciteliaData.ID,
						uciteliaData.PRIEZVISKO, uciteliaData.MENO,
						uciteliaData.INICIALA, uciteliaData.KATEDRA,
						uciteliaData.ODDELENIE, uciteliaData.LOGIN);
				// Log.d("ucitelia", uciteliaData.ID+
				// uciteliaData.PRIEZVISKO+ uciteliaData.MENO+
				// uciteliaData.INICIALA+ uciteliaData.KATEDRA+
				// uciteliaData.ODDELENIE+ uciteliaData.LOGIN);
			}
		} else if (bMiestnosti) {
			bMiestnosti = false;

			// Log.d("miestnosti", miestnostData.NAZOV+
			// miestnostData.KAPACITA+ miestnostData.TYP+
			// miestnostData.AISKOD);

		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		chars = new String(ch, start, length);
	}

}
