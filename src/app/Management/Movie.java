package app.Management;


import java.util.ArrayList;
import java.util.HashSet;

public class Movie {
	private String movieid;
	private String title;
	private String year;
	private String director;
	private String banner_url;
	private String trailer_url;
	private float price;
	
	private HashSet<Star> stars;
	private HashSet<Genre> genres;
	
	public Movie() 
	{
		stars = new HashSet<Star>();
		genres = new HashSet<Genre>();
	
	}
	
	/** Set/Add Methods */
	public void setMovieId(String movieid) 
	{
		this.movieid = movieid;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setYear(String year)
	{
		this.year = year;
	}
	
	public void setDirector(String director)
	{
		this.director = director;
	}
	
	public void setBanner_url(String banner_url)
	{
		this.banner_url = banner_url;
	}
	
	public void setTrailer_url(String trailer_url)
	{
		this.trailer_url = trailer_url;
	}
	
	public void addStar(Star star)
	{
		stars.add(star);
	}
	
	public void addGenre(Genre genre)
	{
		genres.add(genre);
	}
	
	public void setPrice(float price)
	{
		this.price = price;
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
	
	public String getYear()
	{
		return year;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public String getBanner_url()
	{
		return banner_url;
	}
	
	public String getTrailer_url()
	{
		return trailer_url;
	}
	
	public ArrayList<Star> getStars()
	{
		return new ArrayList<Star>(stars);
	}
	
	public ArrayList<Genre> getGenres()
	{
		return new ArrayList<Genre>(genres);
	}
	
	public float getPrice()
	{
		return price;
	}
	
	/** Comparison Method */
	public int hashCode(){
		    return  getMovieid().hashCode();
		              
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }
}
