package sax;

import java.util.HashSet;

public class Movie {
	private String movieid;
	private int id;
	private String title;
	private int year;
	private String director;
	private HashSet<String> genres;

	public Movie()
	{
		this.genres = new HashSet<String>();
	}
	
	/** Set/Add Methods */
	public void setMovieId(String movieid) 
	{
		this.movieid = movieid;
	}
	
	public void setid(int id)
	{
		this.id = id;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public void setDirector(String director)
	{
		this.director = director;
	}
	
	public void setGenre(String genre)
	{
		if(!genres.contains(genre.toLowerCase()))
		{
			genres.add(genre);
		}
	}
	
	/** Accessor Methods */
	public String getMovieid()
	{
		return movieid;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public HashSet<String> getGenres()
	{
		return genres;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String toString(){
		
		return "Film ID: " + this.movieid + "\n" + "Director: " + this.director + "\n" + "Title: " + this.title + "\n" + "Year: " + this.year + "\n" + "Genre: " + this.genres.toString() + "\n";
	}
	
	/** Comparison Method */
	public int hashCode(){
		    return  getMovieid().hashCode();
		              
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }
}
