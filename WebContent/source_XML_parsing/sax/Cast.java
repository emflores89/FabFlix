package sax;

public class Cast {
	
	private String movieid;
	private String starid;
	private String starname;

	public Cast(){
		super();
	}
	
	/** Set Methods */
	public void setMovieId(String movieid){
		this.movieid = movieid;
	}
	
	public void setStarName(String starname)
	{
		this.starname = starname;
	}
	
	public void setStarid(String starid){	
		this.starid = starid;
	}
	
	/** Accessor Methods */
	public String getMovieId() {
		return movieid;
	}
	
	public String getStarId() {
		return starid;
	}
	
	public String getStarName() {
		return starname;
	}
	
	/** To String Method */
	public String toString() {
		
		return movieid + " " + starname;
	}

}
