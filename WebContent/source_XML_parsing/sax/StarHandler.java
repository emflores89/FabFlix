package sax;


import java.util.LinkedHashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/** Multiple stars with different nick names **/

public class StarHandler extends DefaultHandler{
	
	private Map<String,Star> starList = new LinkedHashMap<String,Star>();
	private Star star = null;
	
	boolean bstage = false;
	boolean bdob = false;
	
	
	public Map<String, Star> getStars() 
	{
		return starList;
	}
	
	@Override
	public void warning(SAXParseException e) throws SAXException
	{
		System.out.println("Warning: " + e.getMessage());
	}
	

	@Override
	public void error(SAXParseException e) throws SAXException 
	{
		System.out.println("Error: " + e.getMessage());
	}
	
	@Override
	public void fatalError(SAXParseException e) throws SAXException 
	{
		System.out.println("Fatal error: " + e.getMessage());
	}
	
	@Override
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {

		//System.out.println("Start Element :" + qName);
		if(qName.equalsIgnoreCase("actor"))
		{
			star = new Star();
		}
		
		if (qName.equalsIgnoreCase("stagename")) {
			bstage = true;
		}

		if (qName.equalsIgnoreCase("dob")) {
			bdob = true;
		}

	}
	
	@Override
	public void endElement(String uri, String localName,
			String qName) throws SAXException {

			//System.out.println("End Element :" + qName);
		if (qName.equalsIgnoreCase("actor")) {
			starList.put(star.getName(),star);
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
			
		if (bstage) {
			String stage_name = new String(ch, start, length);
			String[] name = stage_name.split("\\s+");
			if(name.length == 1)
			{
				star.setfirst_name("");
				star.setlast_name(name[0]);
			}
			else
			{
				star.setfirst_name(name[0]);
				star.setlast_name(name[1]);
			}
	
			bstage = false;
		}


		if (bdob) {
			String dob = new String(ch, start, length).trim();
			if(!dob.isEmpty() && dob.matches("[0-9]+"))
			{
				star.setdob(dob);			
			}
			bdob= false;
		}	
	}
	
}
