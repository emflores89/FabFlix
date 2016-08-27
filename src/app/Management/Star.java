package app.Management;

import java.util.ArrayList;
import java.util.HashSet;

public class Star {
	private String id;
	private String first_name;
	private String last_name;
	private String dob;
	private String photo_url;
	private HashSet<Movie> starredin;
	
	public Star(){
		starredin = new HashSet<Movie>();
	}
	
	/** Set Methods */
	public void setId(String id){
		this.id = id;
	}
	
	public void setfirst_name(String first_name){	
		this.first_name = first_name;
	}
	
	public void setlast_name(String last_name){
		this.last_name = last_name;
	}
	
	public void setdob(String dob) {
		this.dob = dob;
	}
	
	public void setPhoto_url(String photo_url) {
		
		this.photo_url = photo_url;
	}
	
	public void addMovie(Movie movie) {
		starredin.add(movie);
	}
	
	/** Accessor Methods */
	public String getid() {
		return id;
	}
	
	public String getfirst_name() {
		return first_name;
	}
	
	public String getlast_name() {
		return last_name;
	}
	
	public String getName()
	{
		if(first_name.isEmpty() || first_name.equals("none"))
		{
			return last_name;
		}
		
		else
		{
			return first_name + " " + last_name;
		}
		
	}
	
	public String getdob() {
		
		return dob;
	}
	
	public String getPhoto_url() {
		
		return photo_url;
	}
	public ArrayList<Movie> getStarredin() {
		
		return new ArrayList<Movie>(starredin);
	}
	
	/** To String Method */
	public String toString() {
		
		if(first_name.isEmpty())
		{
			return last_name;
		}
		
		else
		{
			return first_name + " " + last_name;
		}
		
	}
	
	/** Comparison Method */
	public int hashCode(){
		    return  getid().hashCode();
		              
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }

}
