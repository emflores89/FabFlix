package sax;


import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/** Multiple stars with different nick names **/

public class CastHandler extends DefaultHandler{
	
	private Cast cast = null;
	private Map<String, Star> stars;
	private Map<String, Movie> movies;
	int size = 0;
	
	boolean bfilmid = false;
	boolean bstage = false;
	
	public CastHandler(Map<String,Star> stars, Map<String,Movie> movies)
	{
		this.stars = stars;
		this.movies = movies;
	}
	
	public int getSize()
	{
		return size;
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
		if(qName.equalsIgnoreCase("m"))
		{
			cast = new Cast();
		}
		
		if (qName.equalsIgnoreCase("a")) {
			bstage = true;
		}

		if (qName.equalsIgnoreCase("f")) {
			bfilmid= true;
		}

	}
	
	@Override
	public void endElement(String uri, String localName,
			String qName) throws SAXException {

		//System.out.println("End Element :" + qName);
		
		//Add to the list of movies
		//where this star as been casted in
		//If star doesn't exist
		//ignore
		if (qName.equalsIgnoreCase("m")) {

			Star star = stars.get(cast.getStarName());
			Movie movie = movies.get(cast.getMovieId());
			
			if(star != null & movie != null)
			{
				star.setMovie(movie);
				size++;
			}
		
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
			
		if (bstage) {
			String stage_name = new String(ch, start, length);
			cast.setStarName(stage_name);
			bstage = false;
		}

		if (bfilmid) {
			String filmid = new String(ch, start, length);
			cast.setMovieId(filmid);
			bfilmid= false;
		}	
	}
	
}