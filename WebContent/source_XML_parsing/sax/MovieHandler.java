package sax;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class MovieHandler extends DefaultHandler{
	
	private Map<String,Movie> movieList = new LinkedHashMap<String,Movie>();
	private Map<String,String> genreMap = new HashMap<String,String>();
	
	private Movie movie = null;
	boolean bfilm = false;
	boolean bfid = false;
	boolean btitle = false;
	boolean byear = false;
	boolean bdirn = false;
	boolean bcat = false;
	boolean bcattext = false;
	
	
	public MovieHandler()
	{
		//Map a categroy to a genre
		//For database (Some are missing)
		genreMap.put("biop", "Biography");
		genreMap.put("romt", "Romance");
		genreMap.put("dram", "Drama");
		genreMap.put("scif", "Sci-Fi");
		genreMap.put("porn", "Porn");
		genreMap.put("horr", "Horror");
		genreMap.put("docu", "Documentary");
		genreMap.put("ctxx", "Ctxx");
		genreMap.put("susp", "Suspense");
		genreMap.put("cnr", "Crime/Gangster");
		genreMap.put("musc", "Musical");
		genreMap.put("cart", "Cartoon");
		genreMap.put("hist", "History");
		genreMap.put("cnrb", "Crime/Gangster");
		genreMap.put("fant", "Fantasy");
		genreMap.put("tvmini", "TV-miniseries");
		genreMap.put("actn", "Action");
		genreMap.put("road", "Road-movie");
		genreMap.put("faml", "Family");
		genreMap.put("road", "Road-movie");
		genreMap.put("noir", "Noir");
		genreMap.put("act", "action");
		genreMap.put("advt", "Adventure");
		genreMap.put("comd", "Comedy");
		genreMap.put("myst", "Mystery");
		genreMap.put("west", "Western");
		genreMap.put("bioG", "Biography");
		genreMap.put("hor", "Horror");
		genreMap.put("sxfi", "Sci-Fi");
		genreMap.put("dram>", "Drama");
		genreMap.put("dramd", "Drama");
		genreMap.put("cnrbb", "Crime/Gangster");
		genreMap.put("viol", "Violence");
		genreMap.put("surl","surreal");
		genreMap.put("dram>", "Drama");
		
	}
	
	public void genreMapping(Movie movie, String cat)
	{
		//Docu Dram -> [docu,dram]
		//Docu -> [docu]
		String[] parsed = cat.toLowerCase().split("\\s+");
		String genre = null;
		
		for(String category: parsed)
		{
			genre = genreMap.get(category);
			if(genre != null)
			{
				movie.setGenre(genre);
			}
			else
			{
				movie.setGenre(cat);
			}			
		}
				
	}
	
	public Map<String, Movie> getMovies() 
	{
		return movieList;
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
		if(qName.equalsIgnoreCase("film"))
		{
			movie = new Movie();
			bfilm = true;
		}
		
		if (qName.equalsIgnoreCase("fid")) {
			bfid = true;
		}

		if (qName.equalsIgnoreCase("t")) {
			btitle = true;
		}

		if (qName.equalsIgnoreCase("year")) {
			byear = true;
		}

		if (qName.equalsIgnoreCase("dirn")) {
			bdirn = true;
		}
		
		if (qName.equalsIgnoreCase("cat")) {
			bcat = true;
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName,
			String qName) throws SAXException {

			//System.out.println("End Element :" + qName);
		if (qName.equalsIgnoreCase("film")) {
			if(movie.getDirector() != null && movie.getMovieid()!= null)
			{
				movieList.put(movie.getMovieid(), movie);
				bfilm = false;
			}
		
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
			
		if (bfid) {
			String movieid = new String(ch, start, length);
			movie.setMovieId(movieid);
			//System.out.println("Film ID : " + movieid);
			bfid = false;
		}

		if (btitle) {
			String title = new String(ch, start, length);
			movie.setTitle(title.trim());
			//System.out.println("Title: " + title);
			btitle= false;
		}

		if (byear) {
			String year = new String(ch, start, length).trim();
			
			int year_int = 1900;		
			//I.e No 19yy
			if(!year.matches(".*[a-zA-Z]+.*"))
			{
				year_int = Integer.parseInt(year);
			}	
			movie.setYear(year_int);
			//System.out.println("Year : " + year);
			byear = false;
		}

		if (bdirn) {
			String director = new String(ch, start, length);
			movie.setDirector(director.trim());
			//System.out.println("Director : " + director);
			bdirn = false;
		}
		
		if (bcat) {
			String genre = new String(ch, start, length);
			genreMapping(movie,genre);
			//movie.setGenre(genre);
			//System.out.println(genre);
			bcat = false;	
		}
		

	}
	
}
