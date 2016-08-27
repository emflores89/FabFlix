package sax;


import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.sql.*;



public class SAXParser {
	private Map<String,Movie> movies;
	private Map<String,Star> stars;
	
	private String filmsFile = "/opt/tomcat/webapps/fabflix/source_XML_parsing/stanford-movies/mains243.xml";
	private String actorsFile = "/opt/tomcat/webapps/fabflix/source_XML_parsing/stanford-movies/actors63.xml";
	private String castsFile = "/opt/tomcat/webapps/fabflix/source_XML_parsing/stanford-movies/casts124.xml";
 	
	public void parseMovies(String path_to_xml) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

		MovieHandler handler = new MovieHandler();
		
		File file = new File(path_to_xml);
	    InputStream inputStream = new FileInputStream(file);
	    Reader reader = new InputStreamReader(inputStream);
	    
	    
	    InputSource is = new InputSource(reader);
	    is.setEncoding("ISO-8859-1");
	    saxParser.parse(is, handler);
	    
	    movies = handler.getMovies();

	    inputStream.close();
	    reader.close();
	}
	
	public void praseStars(String path_to_xml) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

		StarHandler handler = new StarHandler();
		
		File file = new File(path_to_xml);
	    InputStream inputStream = new FileInputStream(file);
	    Reader reader = new InputStreamReader(inputStream);
	    
	    
	    InputSource is = new InputSource(reader);
	    is.setEncoding("ISO-8859-1");
	    saxParser.parse(is, handler);
	    
	    stars = handler.getStars();
	    
	    inputStream.close();
	    reader.close();

	}
	
	public void parseCast(String path_to_xml, Map<String,Star> stars, Map<String,Movie> movies) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

		CastHandler handler = new CastHandler(stars,movies);
		
		File file = new File(path_to_xml);
	    InputStream inputStream = new FileInputStream(file);
	    Reader reader = new InputStreamReader(inputStream);
	       
	    InputSource is = new InputSource(reader);
	    is.setEncoding("ISO-8859-1");
	    saxParser.parse(is, handler);
	    
	    inputStream.close();
	    reader.close();
	    
	}
	
	public void DBupload()
	{
		try {
			SQL sql = new SQL();
			Connection connection = sql.getConnection();
			connection.setAutoCommit(false);
			
			System.out.println("..add movies");
			sql.addMovies(movies, connection);
			System.out.println("..add stars");
			sql.addStars(stars, connection);
			System.out.println("..Linking Stars to Movies");
			sql.addStarredIn(stars, connection);
			System.out.println("..genres");
			sql.addGenres(movies, connection);
			connection.setAutoCommit(true);
			connection.close();
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}
	
	public void parse()
	{
		try {
			System.out.println("...Parsing Movies");
			parseMovies(filmsFile);	
			System.out.println("...Parsing Stars");
			praseStars(actorsFile);
			System.out.println("...Parsing Casts");
			parseCast(castsFile,stars,movies);
		
		}
		catch (SAXException e) {
			//Error Handlers will Handle it
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public static void main(String argv[]) {
		
		long start = System.currentTimeMillis();
		
		SAXParser parser = new SAXParser();
		parser.parse();
		parser.DBupload();
		
		long end = System.currentTimeMillis();
		
		System.out.println("Took : " + ((end - start) / 1000));
	}
	
}
