package com.svt.candle.XMLParsing;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import Database.DatabaseManager;
import android.util.Log;
/**
 *  trieda na parsovanie xml a ukladanie do databazy vsetky udaje, xperia8 2:30, sax parser switch 
 */
public class DataHandlerSwitch extends DefaultHandler {
	// na vkladanie udajov do databazy
	private DatabaseManager dbManager = null;
	// zakladne
	private Boolean bUcitelia = false;
	private Boolean bMiestnosti = false;
	private Boolean bPredmety = false;
	private Boolean bHodiny = false;
	

	private HodinaData hodinaData = null;
	private PredmetyData predmetyData = null;
	private MiestrnostData miestnostData = null;
	private UciteliaData uciteliaData = null;
	
	String chars = null;

	public enum Tags {
		TYP, TYPMIESTNOSTI, UCITEL, MIESTNOST, PREDMET, TYPY, TYPYMIESTNOSTI, UCITELIA, 
		MIESTNOSTI, PREDMETY, HODINY, ROZVRH, HODINA
	}

	public enum TagsTypy {
		ID, POPIS
	}

	public enum TagsUcitelia {
		UCITEL, ID, PRIEZVISKO, MENO, INICIALA, KATEDRA, ODDELENIE, LOGIN, UCITELIA
	}

	public enum TagsMiestnosti {
		MIESTNOST, NAZOV, KAPACITA, TYP, AISKOD, MIESTNOSTI
	}

	public enum TagsPredmety {
		PREDMET, NAZOV, KOD, KRATKYKOD, KREDITY, ROZSAH, PREDMETY
	}

	public enum TagsHodiny {
		HODINA, DEN, ZACIATOK, KONIEC, MIESTNOST, TRVANIE, PREDMET, UCITELIA, KRUZKY, TYP, 
		OLDID, ZVIAZANEHODINY, ZVIAZANEOLDID, POZNAMKA, HODINY
	}

	public DataHandlerSwitch(DatabaseManager dbManager) {
		this.dbManager = dbManager;
		predmetyData = new PredmetyData();
		hodinaData = new HodinaData();
		miestnostData = new MiestrnostData();
		uciteliaData = new UciteliaData();
	}


	@Override
	public void startDocument() throws SAXException {
		Log.d("ParserXMl", "Dostal som sa do handlera1");
	}

	@Override
	public void endDocument() throws SAXException {}
	
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, org.xml.sax.Attributes atts) throws SAXException {

		if(bHodiny){
			if (localName.equals("hodina")) {
				hodinaData.ID = atts.getValue("id");
			}
		} else if(bUcitelia){
			if(localName.equals("ucitel")){
				uciteliaData.ID = atts.getValue("id");
			}
		} else if(bPredmety){
			if(localName.equals("predmet")){
				predmetyData.ID = atts.getValue("id");
			}
		} else if(bMiestnosti){ //setrenie casu
		} else if(localName.equals("typ")){
			dbManager.insertTypHodin(atts.getValue("id"),atts.getValue("popis"));
		} else if(localName.equals("typmiestnosti")){
			 dbManager.insertTypMiestnosti(atts.getValue("id"),atts.getValue("popis"));
		} else if(localName.equals("hodiny")){
			bHodiny = true;
			Log.d("parsovanie", "hodiny");
		} else if(localName.equals("ucitelia")){
			bUcitelia = true;
			Log.d("parsovanie", "ucitelia");
		} else if(localName.equals("predmety")){
			bPredmety = true;
			Log.d("parsovanie", "predmety");
		} else if(localName.equals("miestnosti")){
			bMiestnosti = true;
			Log.d("parsovanie", "miestnosti");
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {

		if (bHodiny) {
			TagsHodiny tag = TagsHodiny.valueOf(localName.toUpperCase());

			switch (tag) {
			case DEN:
				hodinaData.DEN = chars;
				break;
			case ZACIATOK:
				hodinaData.ZACIATOK = chars;
				break;
			case KONIEC:
				hodinaData.KONIEC = chars;
				break;
			case MIESTNOST:
				hodinaData.MIESTNOST = chars;
				break;
			case TRVANIE:
				hodinaData.TRVANIE = chars;
				break;
			case PREDMET:
				hodinaData.PREDMET = chars;
				break;
			case UCITELIA:
				hodinaData.UCITELIA = chars;
				break;
			case KRUZKY:
				hodinaData.KRUZKY = chars;
				break;
			case TYP:
				hodinaData.TYP = chars;
				break;
			case OLDID:
				hodinaData.OLDID = chars;
				break;
			case POZNAMKA:
				hodinaData.POZNAMKA = chars;
				break;
			case HODINY:
				bHodiny = false;
				break;
			case HODINA:
				dbManager.insertHodina(hodinaData.ID, hodinaData.DEN,
						hodinaData.ZACIATOK, hodinaData.KONIEC,
						hodinaData.MIESTNOST, hodinaData.TRVANIE,
						hodinaData.PREDMET, hodinaData.UCITELIA,
						hodinaData.KRUZKY, hodinaData.TYP, hodinaData.OLDID,
						hodinaData.POZNAMKA);

//				String delims = "[,]";
//				String[] ucitelia = hodinaData.UCITELIA.split(delims);
//				for (int i = 0; i < ucitelia.length; i++) {
//					dbManager.insertHodUcitel(hodinaData.ID, ucitelia[i]);
//				}
//				String[] kruzky = hodinaData.KRUZKY.split(delims);
//				for (int i = 0; i < kruzky.length; i++) {
//					dbManager.insertHodKruzok(hodinaData.ID, kruzky[i]);
//				}
				break;
			}

		} else if (bPredmety) {

			TagsPredmety tag = TagsPredmety.valueOf(localName.toUpperCase());

			switch (tag) {
			case NAZOV:
				predmetyData.NAZOV = chars;
				break;
			case KOD:
				predmetyData.KOD = chars;
				break;
			case KRATKYKOD:
				predmetyData.KRATKYKOD = chars;
				break;
			case KREDITY:
				predmetyData.KREDITY = chars;
				break;
			case ROZSAH:
				predmetyData.ROZSAH = chars;
				break;
			case PREDMETY:
				bPredmety = false;
				break;
			case PREDMET:
				dbManager.insertPredmety(predmetyData.ID, predmetyData.NAZOV,
						predmetyData.KOD, predmetyData.KRATKYKOD,
						predmetyData.KREDITY, predmetyData.ROZSAH);
				break;
			}

		} else if (bMiestnosti) {

			TagsMiestnosti tag = TagsMiestnosti
					.valueOf(localName.toUpperCase());

			switch (tag) {
			case NAZOV:
				miestnostData.NAZOV = chars;
				break;
			case KAPACITA:
				miestnostData.KAPACITA = chars;
				break;
			case AISKOD:
				miestnostData.AISKOD = chars;
				break;
			case TYP:
				miestnostData.TYP = chars;
				break;
			case MIESTNOST:
				dbManager.insertMiestnost(miestnostData.NAZOV,
						miestnostData.KAPACITA, miestnostData.TYP,
						miestnostData.AISKOD);
				break;
			case MIESTNOSTI:
				bMiestnosti = false;
				break;
			}

		} else if (bUcitelia) {
//
//			TagsUcitelia tag = TagsUcitelia.valueOf(localName.toUpperCase());
//
//			switch (tag) {
//			case MENO:
//				uciteliaData.MENO = chars;
//				break;
//			case PRIEZVISKO:
//				uciteliaData.PRIEZVISKO = chars;
//				break;
//			case KATEDRA:
//				uciteliaData.KATEDRA = chars;
//				break;
//			case ODDELENIE:
//				uciteliaData.ODDELENIE = chars;
//				break;
//			case INICIALA:
//				uciteliaData.INICIALA = chars;
//				break;
//			case LOGIN:
//				uciteliaData.LOGIN = chars;
//				break;
//			case UCITELIA:
//				bUcitelia = false;
//				break;
//			case UCITEL:
//				dbManager.insertUcitel(uciteliaData.ID,
//						uciteliaData.PRIEZVISKO, uciteliaData.MENO,
//						uciteliaData.INICIALA, uciteliaData.KATEDRA,
//						uciteliaData.ODDELENIE, uciteliaData.LOGIN);
//				break;
//			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		chars = new String(ch, start, length);
		//chars = chars.trim();	
	}
}
