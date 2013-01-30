package com.svt.candle.XMLParsing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import Database.DatabaseManager;
import android.util.Log;

public class ParserXML {
	private DatabaseManager dbManager = null;
	private InputStream inputStream = null;
	
	public ParserXML(InputStream inputStream, DatabaseManager dbManager) {
		this.inputStream = inputStream;	
		this.dbManager = dbManager;  
		parseXml();
	}
// iba na testovanie
	public void testSpravnehoCitaniaSuboru(){
		BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder total = new StringBuilder();
		String line;
		try {
			while ((line = r.readLine()) != null) {
			    total.append(line);
				
			}
		} catch (IOException e) {
			Log.e("Parser", e.getMessage());
		}
		Log.d("Parser - test", total.toString());
		Log.d("Parser", "koniec testu");
	}
	

	private void parseXml() {
		
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			DataHandler dataHandler = new DataHandler(dbManager);
			xr.setContentHandler(dataHandler);
			
			InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
			InputSource inputSource = new InputSource(isr);
			inputSource.setEncoding("UTF-8");
			sp.parse(inputSource, dataHandler);
		} catch (ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error - ParserConfigurationException" + pce.getMessage(), pce);
		} catch (SAXException se) {
			Log.e("SAX XML", "sax error" + se.getMessage(), se);
		} catch (IOException ioe) {
			Log.e("SAX XML", "sax parse io error", ioe);
		} catch (Exception e) {
			Log.e("SAX XML", "sax parse error exception" + e.getMessage(), e);
		}
	}
}
