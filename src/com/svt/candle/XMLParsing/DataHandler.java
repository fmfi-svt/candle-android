package com.svt.candle.XMLParsing;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import Database.DatabaseManager;
import android.util.Log;

public class DataHandler extends DefaultHandler {
	// na vkladanie udajov do databazy
	private DatabaseManager dbManager = null;
	// booleans that check whether it's in a specific tag or not
	// zakladne
	private Boolean bUcitelia = false;
	private Boolean bMiestnosti = false;
	private Boolean bPredmety = false;
	private Boolean bHodiny = false;
	// ucitel
	private Boolean bUcitelPriezvisko = false;
	private Boolean bUcitelMeno = false;
	private Boolean bUcitelIniciala = false;
	private Boolean bUcitelKatedra = false;
	private Boolean bUcitelOddelenie = false;
	private Boolean bUcitelLogin = false;
	// miestnost
	private Boolean bMiestnostNazov = false;
	private Boolean bMiestnostKapacita = false;
	private Boolean bMiestnostTyp = false;
	private Boolean bMiestnostAisKod = false;
	// predmet
	private Boolean bPredmetNazov = false;
	private Boolean bPredmetKod = false;
	private Boolean bPredmetKratkyKod = false;
	private Boolean bPredmetKredity = false;
	private Boolean bPredmetRozsah = false;
	// hodina
	private Boolean bHodinaDen = false;
	private Boolean bHodinaZaciatok = false;
	private Boolean bHodinaKoniec = false;
	private Boolean bHodinaMiestnost = false;
	private Boolean bHodinaTrvanie = false;
	private Boolean bHodinaPredmet = false;
	private Boolean bHodinaUcitelia = false;
	private Boolean bHodinaKruzky = false;
	private Boolean bHodinaTyp = false;
	private Boolean bHodinaOldID = false;
	private Boolean bHodinaPoznamka = false;

	private String TYP = "typ";
	private String TYPMIESTNOSTI = "typmiestnosti";

	private HodinaData hodinaData = null;
	private PredmetyData predmetyData = null;
	private MiestrnostData miestnostData = null;
	private UciteliaData uciteliaData = null;

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

	public DataHandler(DatabaseManager dbManager) {
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

		if (bHodiny) {
			TagsHodiny tag = TagsHodiny.valueOf(localName.toUpperCase());

			switch (tag) {
			case HODINA:
				hodinaData.ID = atts.getValue("id");
				break;
			case DEN:
				bHodinaDen = true;
				break;
			case ZACIATOK:
				bHodinaZaciatok = true;
				break;
			case KONIEC:
				bHodinaKoniec = true;
				break;
			case MIESTNOST:
				bHodinaMiestnost = true;
				break;
			case TRVANIE:
				bHodinaTrvanie = true;
				break;
			case PREDMET:
				bHodinaPredmet = true;
				break;
			case UCITELIA:
				bHodinaUcitelia = true;
				break;
			case KRUZKY:
				bHodinaKruzky = true;
				break;
			case TYP:
				bHodinaTyp = true;
				break;
			case POZNAMKA:
				bHodinaPoznamka = true;
				break;
			case OLDID:
				bHodinaOldID = true;
				break;
			}

		} else if (bPredmety) {

			TagsPredmety tag = TagsPredmety.valueOf(localName.toUpperCase());

			switch (tag) {
			case PREDMET:
				predmetyData.ID = atts.getValue("id");
				break;
			case NAZOV:
				bPredmetNazov = true;
				break;
			case KOD:
				bPredmetKod = true;
				break;
			case KRATKYKOD:
				bPredmetKratkyKod = true;
				break;
			case KREDITY:
				bPredmetKredity = true;
				break;
			case ROZSAH:
				bPredmetRozsah = true;
				break;
			}

		} else if (bMiestnosti) {

			TagsMiestnosti tag = TagsMiestnosti
					.valueOf(localName.toUpperCase());

			switch (tag) {
			case NAZOV:
				bMiestnostNazov = true;
				break;
			case KAPACITA:
				bMiestnostKapacita = true;
				break;
			case AISKOD:
				bMiestnostAisKod = true;
				break;
			case TYP:
				bMiestnostTyp = true;
				break;
			}

		} else if (bUcitelia) {

			TagsUcitelia tag = TagsUcitelia.valueOf(localName.toUpperCase());

			switch (tag) {
			case MENO:
				bMiestnostNazov = true;
				break;
			case PRIEZVISKO:
				bMiestnostKapacita = true;
				break;
			case KATEDRA:
				bMiestnostAisKod = true;
				break;
			case ODDELENIE:
				bMiestnostTyp = true;
				break;
			case INICIALA:
				bMiestnostTyp = true;
				break;
			case LOGIN:
				bMiestnostTyp = true;
				break;
			case UCITEL:
				uciteliaData.ID = atts.getValue("id");
				break;
			}

		} else if (localName.equalsIgnoreCase(TYP)) {

			dbManager.insertTypHodin(atts.getValue("id"),
					atts.getValue("popis"));

		} else if (localName.equalsIgnoreCase(TYPMIESTNOSTI)) {
			dbManager.insertTypMiestnosti(atts.getValue("id"),
					atts.getValue("popis"));

		} else {
			Log.d("parsovaniemaintag", localName);
			Tags mainTag = Tags.valueOf(localName.toUpperCase());
			switch (mainTag) {
			case HODINY:
				bHodiny = true;
				break;
			case UCITELIA:
				bUcitelia = true;
				break;
			case MIESTNOSTI:
				bMiestnosti = true;
				break;
			case PREDMETY:
				bPredmety = true;
				break;
			}
		}

	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {

		if (bHodiny) {
			TagsHodiny tag = TagsHodiny.valueOf(localName.toUpperCase());

			switch (tag) {
			case DEN:
				bHodinaDen = false;
				break;
			case ZACIATOK:
				bHodinaZaciatok = false;
				break;
			case KONIEC:
				bHodinaKoniec = false;
				break;
			case MIESTNOST:
				bHodinaMiestnost = false;
				break;
			case TRVANIE:
				bHodinaTrvanie = false;
				break;
			case PREDMET:
				bHodinaPredmet = false;
				break;
			case UCITELIA:
				bHodinaUcitelia = false;
				break;
			case KRUZKY:
				bHodinaKruzky = false;
				break;
			case TYP:
				bHodinaTyp = false;
				break;
			case OLDID:
				bHodinaOldID = false;
				break;
			case POZNAMKA:
				bHodinaPoznamka = false;
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

				String delims = "[,]";
				String[] ucitelia = hodinaData.UCITELIA.split(delims);
				for (int i = 0; i < ucitelia.length; i++) {
					dbManager.insertHodUcitel(hodinaData.ID, ucitelia[i]);
				}
				String[] kruzky = hodinaData.KRUZKY.split(delims);
				for (int i = 0; i < kruzky.length; i++) {
					dbManager.insertHodKruzok(hodinaData.ID, kruzky[i]);
				}
				break;
			}

		} else if (bPredmety) {

			TagsPredmety tag = TagsPredmety.valueOf(localName.toUpperCase());

			switch (tag) {
			case NAZOV:
				bPredmetNazov = false;
				break;
			case KOD:
				bPredmetKod = false;
				break;
			case KRATKYKOD:
				bPredmetKratkyKod = false;
				break;
			case KREDITY:
				bPredmetKredity = false;
				break;
			case ROZSAH:
				bPredmetRozsah = false;
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
				bMiestnostNazov = false;
				break;
			case KAPACITA:
				bMiestnostKapacita = false;
				break;
			case AISKOD:
				bMiestnostAisKod = false;
				break;
			case TYP:
				bMiestnostTyp = false;
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

			TagsUcitelia tag = TagsUcitelia.valueOf(localName.toUpperCase());

			switch (tag) {
			case MENO:
				bMiestnostNazov = false;
				break;
			case PRIEZVISKO:
				bMiestnostKapacita = false;
				break;
			case KATEDRA:
				bMiestnostAisKod = false;
				break;
			case ODDELENIE:
				bMiestnostTyp = false;
				break;
			case INICIALA:
				bMiestnostTyp = false;
				break;
			case LOGIN:
				bMiestnostTyp = false;
				break;
			case UCITELIA:
				bUcitelia = false;
				break;
			case UCITEL:
				dbManager.insertUcitel(uciteliaData.ID,
						uciteliaData.PRIEZVISKO, uciteliaData.MENO,
						uciteliaData.INICIALA, uciteliaData.KATEDRA,
						uciteliaData.ODDELENIE, uciteliaData.LOGIN);
				break;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		String chars = new String(ch, start, length);
		chars = chars.trim();
		if (bHodiny) {

			if (bHodinaDen)
				hodinaData.DEN = chars;
			else if (bHodinaZaciatok)
				hodinaData.ZACIATOK = chars;
			else if (bHodinaKoniec)
				hodinaData.KONIEC = chars;
			else if (bHodinaMiestnost)
				hodinaData.MIESTNOST = chars;
			else if (bHodinaTrvanie)
				hodinaData.TRVANIE = chars;
			else if (bHodinaPredmet)
				hodinaData.PREDMET = chars;
			else if (bHodinaUcitelia)
				hodinaData.UCITELIA = chars;
			else if (bHodinaKruzky)
				hodinaData.KRUZKY = chars;
			else if (bHodinaTyp)
				hodinaData.TYP = chars;
			else if (bHodinaOldID)
				hodinaData.OLDID = chars;
			else if (bHodinaPoznamka)
				hodinaData.POZNAMKA = chars;

		} else if (bPredmety) {

			if (bPredmetNazov)
				predmetyData.NAZOV = chars;
			else if (bPredmetKod)
				predmetyData.KOD = chars;
			else if (bPredmetKratkyKod)
				predmetyData.KRATKYKOD = chars;
			else if (bPredmetKredity)
				predmetyData.KREDITY = chars;
			else if (bPredmetRozsah)
				predmetyData.ROZSAH = chars;

		} else if (bMiestnosti) {
			if (bMiestnostNazov)
				miestnostData.NAZOV = chars;
			else if (bMiestnostKapacita)
				miestnostData.KAPACITA = chars;
			else if (bMiestnostTyp)
				miestnostData.TYP = chars;
			else if (bMiestnostAisKod)
				miestnostData.AISKOD = chars;

		} else if (bUcitelia) {
			if (bUcitelMeno)
				uciteliaData.MENO = chars;
			else if (bUcitelPriezvisko)
				uciteliaData.PRIEZVISKO = chars;
			else if (bUcitelIniciala)
				uciteliaData.INICIALA = chars;
			else if (bUcitelKatedra)
				uciteliaData.KATEDRA = chars;
			else if (bUcitelOddelenie)
				uciteliaData.ODDELENIE = chars;
			else if (bUcitelLogin)
				uciteliaData.LOGIN = chars;
		}
	}

}
