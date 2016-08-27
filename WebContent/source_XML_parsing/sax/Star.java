package sax;

import java.util.HashSet;
import java.util.Set;


public class Star {
	private int id;
	private String first_name;
	private String last_name;
	private String dob = null;
	private Set<Movie> starredIn;
	
	public Star(){
		this.starredIn = new HashSet<Movie>();
	}
	
	/** Set Methods */
	public void setId(int id){
		this.id = id;
	}
	
	public void setfirst_name(String first_name){	
		this.first_name = first_name;
	}
	
	public void setlast_name(String last_name){
		this.last_name = last_name;
	}
	
	public void setdob(String dob) {
		
		this.dob = dob + "-1-1";
	
	}
	
	public void setMovie(Movie movie) {
		starredIn.add(movie);
	}
	
	/** Accessor Methods */
	public int getid() {
		return id;
	}
	
	public Set<Movie> getStarredIn()
	{
		return starredIn;
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
		    return  getName().hashCode();
		              
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }

}
